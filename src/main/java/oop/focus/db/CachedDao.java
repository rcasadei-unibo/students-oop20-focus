package oop.focus.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import oop.focus.common.Action;
import oop.focus.db.exceptions.ConnectionException;
import oop.focus.db.exceptions.DaoAccessException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A dao that keeps an internal cache to limit the number of interactions with the source.
 * All data present in the source is loaded during the instantiation of the dao object
 * and it is assumed that all interactions with the source are made using the same instance of the dao object.
 * Cached dao does not guarantee any synchronization between different instances of the same object.
 * Changes made to the data source through another instance of the dao object
 * will not affect the cache state of other instances of the same object.
 *
 * @param <X> the type parameter
 */
public class CachedDao<X> implements SingleDao<X> {

    private static final String ERROR_MESSAGE = "Load error";

    /**
     * The enum Db action.
     */
    private enum DbAction {
        /**
         * The Insert syntax.
         */
        INSERT("INSERT INTO %s VALUES(null,%s) %s", a -> a.map(elem -> "?").collect(Collectors.joining(","))),
        /**
         * The Delete syntax.
         */
        DELETE("DELETE FROM %s %s WHERE id=%s", a -> ""),
        /**
         * The select syntax for multiple elements.
         */
        SELECT_ALL("SELECT * FROM %s %s %s", a -> ""),
        /**
         * The Update syntax.
         */
        UPDATE("UPDATE %s SET %s WHERE id=%s", a -> a.map(elem -> elem + "=?").collect(Collectors.joining(",")));
        private final String syntax;
        private final Function<Stream<String>, String> mapper;
        DbAction(final String s, final Function<Stream<String>, String> mapper) {
            this.syntax = s;
            this.mapper = mapper;
        }
        /**
         * Gets a formatted string with the right syntax to execute a query.
         *
         * @param name the name of the element
         * @param list the list of values
         * @param id   the id of the element
         * @return a string containing the query;
         */
        public String getSyntax(final String name, final List<String> list, final int id) {
            final String stringId;
            if (id != -1) {
                stringId = String.valueOf(id);
            } else {
                stringId = "";
            }
            return String.format(this.syntax, name, this.mapper.apply(list.stream()), stringId);
        }
    }
    private static final int NO_ID = -1;
    private final Connector<Connection> db;
    private final DataSourceParser<X> parser;
    /**
     * The Cache containing the elements already retrieved from the source.
     */
    private final Map<Integer, X> cache;
    private final ObservableSet<X> observable;
    /**
     * Instantiates a new Cached dao.
     *
     * @param parser the parser
     */
    public CachedDao(final DataSourceParser<X> parser) {
        this.db = new H2Connector();
        try {
            this.db.create();
        } catch (final ConnectionException e) {
            e.printStackTrace();
        }
        this.cache = new HashMap<>();
        this.observable = FXCollections.observableSet(new HashSet<>());
        this.parser = parser;
        this.getAllFromSource();
    }

    private void withNoParameters() {
        final Map<Integer, List<String>> values = new HashMap<>();
        try (var s = this.db.getConnection().createStatement()) {
            final var resultSet = s.executeQuery(DbAction.SELECT_ALL.getSyntax(this.parser.getTypeName(),
                    this.parser.getFieldNames(), CachedDao.NO_ID));
            while (resultSet.next()) {
                final List<String> tmp = new ArrayList<>();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    tmp.add(resultSet.getString(i));
                }
                if (!tmp.isEmpty()) {
                    values.put(resultSet.getInt(1), new ArrayList<>(tmp));
                }
            }
        } catch (final SQLException throwables) {
            throw new IllegalStateException();
        }
        this.cache.putAll(values.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        v -> this.parser.create(v.getValue())))
                .entrySet().stream().filter(e -> e.getValue().isPresent())
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().get())));
    }

    private void withParameters(final X x, final DbAction action, final int id) {
        try (var p = this.db.getConnection()
                .prepareStatement(action.getSyntax(this.parser.getTypeName(),
                        this.parser.getFieldNames(), id), Statement.RETURN_GENERATED_KEYS)) {
            final var values = this.parser.getValues(x);
            for (int i = 0; i < values.size(); i++) {
                p.setString(i + 1, values.get(i));
            }
            p.executeUpdate();
            final var generatedKeys = p.getGeneratedKeys();
            while (generatedKeys.next()) {
                final long tmp = generatedKeys.getLong(1);
                this.cache.put(this.getKey(id, tmp), x);
            }
        } catch (final SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    private int getKey(final int id, final long tmp) {
        return (int) (tmp == 0 ? id : tmp);
    }

    private void execute(final Action a) throws ConnectionException {
        this.db.open();
        a.execute();
        this.db.close();
    }

    private void getAllFromSource() {
        try {
            this.execute(this::withNoParameters);
            this.observable.addAll(this.cache.values());
        } catch (final ConnectionException e) {
            System.err.println(ERROR_MESSAGE);
        }
    }

    private Optional<Integer> getIdFromCache(final X x) {
        return this.cache.keySet().stream()
                .filter(a -> this.cache.get(a).equals(x))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     *
     * @return a unmodifiable {@link ObservableSet} to prevent external modifications.
     */
    @Override
    public ObservableSet<X> getAll() {
        return FXCollections.unmodifiableObservableSet(this.observable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final X x) throws DaoAccessException {
        if (this.observable.contains(x)) {
            return;
        }
        try {
            this.execute(() -> this.withParameters(x, DbAction.INSERT, NO_ID));
        } catch (final ConnectionException e) {
            throw new DaoAccessException();
        }
        this.observable.add(x);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final X x) throws DaoAccessException {
        if (!this.observable.contains(x)) {
            throw new IllegalArgumentException();
        }
        this.observable.remove(x);
        this.observable.add(x);
        final Optional<Integer> optId = this.getId(x);
        final int id = optId.orElseThrow();
        try {
            this.execute(() -> this.withParameters(x, DbAction.UPDATE, id));
        } catch (final ConnectionException e) {
            throw new DaoAccessException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final X x) throws DaoAccessException {
        if (!this.observable.contains(x)) {
            throw new IllegalArgumentException();
        }
        this.observable.remove(x);
        final Optional<Integer> optId = this.getId(x);
        final int id = optId.orElseThrow();
        this.cache.remove(id);
        try {
            this.execute(() -> {
                try {
                    this.db.getConnection().createStatement()
                            .execute(DbAction.DELETE.getSyntax(this.parser.getTypeName(),
                                    this.parser.getFieldNames(), id));
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (final ConnectionException e) {
            throw new DaoAccessException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<X> getValue(final int id) {
        return Optional.ofNullable(this.cache.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<Integer> getId(final X x) {
        return this.getIdFromCache(x);
    }
}
