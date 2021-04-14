package oop.focus.calendar.persons.controller;

import javafx.collections.ObservableList;
import oop.focus.common.View;
import oop.focus.db.DataSource;
import oop.focus.homepage.model.Person;

public interface PersonsController {

    void addPerson(Person person);

    void deletePerson(Person person);

    ObservableList<Person> getPersons();

    DataSource getDsi();

    ObservableList<String> getDegree();

    View getView();

}