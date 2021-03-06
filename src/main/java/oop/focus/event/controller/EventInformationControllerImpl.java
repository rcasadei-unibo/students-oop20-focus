package oop.focus.event.controller;
import oop.focus.common.Repetition;
import oop.focus.common.View;
import oop.focus.db.DataSource;
import oop.focus.event.view.EventInformationViewImpl;
import oop.focus.event.model.Event;
import oop.focus.event.model.EventManager;
import oop.focus.event.model.EventManagerImpl;
import oop.focus.calendar.persons.model.Person;

import org.joda.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventInformationControllerImpl implements EventInformationController {

    private final EventInformationViewImpl view;
    private final EventManager eventManager;
    private final EventMenuController menuController;
    private final Event event;

    public EventInformationControllerImpl(final DataSource dsi, final Event event, final EventMenuController menuController) {
        this.eventManager = new EventManagerImpl(dsi);
        this.event = event;
        this.menuController = menuController;
        this.view = new EventInformationViewImpl(this);
    }

    public final Event getEvent() {
        return this.event;
    }

    public final void stopRepetition() {
        this.eventManager.generateRepeatedEvents(LocalDate.now());

        this.event.stopRepeat();
        this.eventManager.removeEvent(this.event);
        this.event.setRepetition(Repetition.ONCE);
        this.eventManager.addEvent(this.event);
    }

    @Override
    public final View getView() {
        return this.view;
    }

    @Override
    public final EventMenuController getMenu() {
        return this.menuController;
    }

    @Override
    public final ObservableList<String> getPersons() {
        final ObservableList<String> list = FXCollections.observableArrayList();
        for (final Person p : this.event.getPersons()) {
            list.add(p.toString());
        }
        return list;
    }

}
