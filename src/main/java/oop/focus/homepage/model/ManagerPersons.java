package oop.focus.homepage.model;

import java.util.List;

public interface ManagerPersons {

    /**
     * This method is use to add new Person to the database.
     * @param person is the person to add.
     */
    void addPerson(Person person);

    /**
     * This method is use to get all the saved persons.
     * @return a list of persons.
     */
    List<Person> getPersons();

    /**
     * This method is use to remove a person from the database if it's already saved.
     * @param person is the person to remove from the database.
     */
    void removePerson(Person person);
}
