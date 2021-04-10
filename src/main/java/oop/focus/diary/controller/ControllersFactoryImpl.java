package oop.focus.diary.controller;

import oop.focus.homepage.model.EventManager;


public class ControllersFactoryImpl implements ControllersFactory {
    private final EventManager manager;
    public ControllersFactoryImpl(final EventManager manager) {
        this.manager = manager;
    }
    @Override
    public final CounterControllerImpl createTimer() {
        return new CounterControllerImpl(manager, true);
    }

    @Override
    public final CounterControllerImpl createStopwatch() {
        return new CounterControllerImpl(manager, false);
    }
}
