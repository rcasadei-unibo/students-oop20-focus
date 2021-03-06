package oop.focus.diary.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import oop.focus.common.View;
import oop.focus.diary.view.EventCounterView;
import oop.focus.event.model.EventManager;

/**
 * Implementation of {@link EventCounterController}. The class manages events, which are used by counters to set
 * the name of activity they are computing.
 */
public class EventCounterControllerImpl implements EventCounterController {
    private final EventCounterView view;
    private final ObservableSet<String> set;
    private final GeneralCounterController generalControllerCounter;

    /**
     * Instantiates a new event counter controller and creates the associated view.
     * @param eventManager  the eventManager
     * @param generalControllerCounter  the generalControllerCounter
     */
    public EventCounterControllerImpl(final EventManager eventManager, final GeneralCounterController
            generalControllerCounter) {
        this.set = FXCollections.observableSet();
        eventManager.getAll().forEach(s -> this.set.add(s.getName()));
        this.view = new EventCounterView(this);
        this.generalControllerCounter = generalControllerCounter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ObservableSet<String> getEventsNames() {
        return this.set;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void disableButton(final boolean disable) {
        this.generalControllerCounter.disableButton(disable);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void disableChooseEvent(final boolean disable) {
        this.view.disableComponent(disable);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void setChosen(final String eventChosen) {
        this.generalControllerCounter.setCounterName(eventChosen);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final void addEvent(final String newEvent) {
        this.set.add(newEvent);
        this.view.updateInput(newEvent);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final View getView() {
        return this.view;
    }
}
