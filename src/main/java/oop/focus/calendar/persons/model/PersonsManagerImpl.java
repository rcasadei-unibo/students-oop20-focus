package oop.focus.calendar.persons.model;


import javafx.collections.ObservableSet;
import oop.focus.db.Dao;
import oop.focus.db.DataSource;
import oop.focus.db.exceptions.DaoAccessException;

public class PersonsManagerImpl implements PersonsManager {

    private final Dao<Person> sd;

    /**
     * This is the class constructor.
     * @param dsi is the DataSource.
     */
    public PersonsManagerImpl(final DataSource dsi) {
        this.sd = dsi.getPersons();
    }

    public final void addPerson(final Person person) {
        if (!this.sd.getAll().contains(person)) {
            try {
                this.sd.save(person);
            } catch (final DaoAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public final ObservableSet<Person> getPersons() {
        return this.sd.getAll();
    }

    public final void removePerson(final Person person) {
        try {
            this.sd.delete(person);
        } catch (final DaoAccessException e) {
            e.printStackTrace();
        }
    }

}
