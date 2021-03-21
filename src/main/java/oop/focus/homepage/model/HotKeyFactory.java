package oop.focus.homepage.model;

/**
 * This interface is used to build the three different types of hotkeys.
 * It is also used to implement the function action based on the category to which the hotkey belongs.
 */
public interface HotKeyFactory {

    /**
     * This method is use for create an activity hotKey.
     * @param name is the name of the hot key to create.
     * @return an hot key of type activity.
     */
     HotKey createActivityHotKey(String name);

    /**
     * This method is use for create a counter hotKey. 
     * @param name is the name of the counter hot key to create.
     * @return an hot key of type counter.
     */
     HotKey createCounterHotKey(String name);

    /**
     * This method is use for create an event hotKey.
     * @param name is the name of the hot key to create.
     * @return an hot key of type event.
     */
     HotKey createEventHotKey(String name);

     /**
      * This method is used to create a keyboard shortcut of a specific type based on the type id.
      * @param name is the name of the hot key to create.
      * @param id is the id of the hot key type.
      * @return an hot key of type x (where x is the type of hot key specified by the id).
      */
     HotKey createFromType(String name, int id);
}
