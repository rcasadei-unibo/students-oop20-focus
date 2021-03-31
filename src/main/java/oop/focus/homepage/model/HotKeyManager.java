package oop.focus.homepage.model;

import java.util.List;
import java.util.Set;

import org.joda.time.LocalDateTime;

/**
 * This is the interface that models the handling of hot keys of all categories.
 */
public interface HotKeyManager {

    /**
     * This method is used to perform the "action" method on a specific hot key.
     * Obviously a hot key has a category and the action varies according to that.
     * @param hotKey is the hot key on which to perform the action.
     * @param start is the start.
     * @param end is the end.
     */
    void action(HotKey hotKey, LocalDateTime start, LocalDateTime end);

    /**
     * This method is used to add an hot keys.
     * @param hotKey is the hot key that must be added.
     */
    void add(HotKey hotKey);

    /**
     * This method is used to add a set of hot keys.
     * @param hotKeys is the set of hot keys that must be added.
     */
    void addAll(Set<HotKey> hotKeys);

    /**
     * This method is used to get the set of all the hot keys(of all categories).
     * @return a set of hot keys.
     */
    List<HotKey> getAll();

    /**
     * This method is used to get the category of a specific hot key.
     * @param hotKey is the hot key whose category you want to know.
     * @return a member of the HotKeyType enumeration.
     */
    HotKeyType getCategory(HotKey hotKey);

    /**
     * This method is used to get all of the events generated after clicking an hot key.
     * @return a set of events generated after clicking an hot key.
     */
    List<Event> getEventsHotKey();

    long getClickCount(String name);

    /**
     * is used to remove a specific hot key from the collection containing all hot keys. 
     * @param hotKey is the hot key that must be remove.
     */
    void remove(HotKey hotKey);

    /**
     * This method is used to remove a set of hot key from the collection containing all hot keys.
     * @param hotKeys is the set of hot keys to remove from the collection.
     */
    void removeAll(Set<HotKey> hotKeys);

}