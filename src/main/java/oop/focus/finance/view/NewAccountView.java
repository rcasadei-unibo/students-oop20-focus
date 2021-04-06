package oop.focus.finance.view;

public interface NewAccountView extends View {

    /**
     * Closes the new account creation window.
     */
    void close();

    /**
     * Saves the account in the database and closes the window.
     */
    void save();
}