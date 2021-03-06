package oop.focus.db;

import javafx.collections.ObservableSet;
import oop.focus.db.exceptions.DaoAccessException;

/**
 * The Dao interface models a generic Data Access Object.
 * It defines the standard methods to access data from any source
 *
 * @param <X> the type parameter
 */
public interface Dao<X> {
    /**
     * Can be used to get all the elements present in the source.
     *
     * @return a {@link ObservableSet} containing all the elements of type X present in the source.
     */
    ObservableSet<X> getAll();

    /**
     * Store an element of type X to the source.
     *
     * @param x the element to store
     * @throws DaoAccessException if the element cannot be stored
     */
    void save(X x) throws DaoAccessException;

    /**
     * Update the stored element in the source with the new element.
     * If an identifying property of the element is changed,
     * the element will be no longer retrieved and updated from the source.
     *
     * @param x the element to be updated
     * @throws IllegalArgumentException if the element is not present in the source
     * @throws DaoAccessException       if the element cannot be updated
     */
    void update(X x) throws DaoAccessException;

    /**
     * Delete the element from the source if present.
     *
     * @param x the element to be deleted
     * @throws DaoAccessException if the element cannot be deleted
     */
    void delete(X x) throws DaoAccessException;
}
