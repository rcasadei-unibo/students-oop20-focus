package oop.focus.diary.controller;

import oop.focus.common.Controller;
import oop.focus.common.View;
import oop.focus.diary.view.GeneralCounterView;
import oop.focus.homepage.model.EventManager;
import org.joda.time.LocalTime;

import java.util.List;

public class GeneralControllerCounter implements Controller {
    private final EventCounterController eventCounterController;
    private final TotalTimeController totalTimeController;
    private final CounterController counterController;
    private final InsertTimeTimerController timeTimerController;
    private LocalTime localTime = LocalTime.MIDNIGHT;
    private String eventName;
    public GeneralControllerCounter(final EventManager eventManager, final boolean isTimer) {
        this.counterController = new CounterControllerImpl(eventManager, isTimer, this);
        this.eventCounterController = new EventCounterController(eventManager, this);
        this.totalTimeController = new TotalTimeControllerImpl(eventManager);
        this.timeTimerController = new InsertTimeTimerController(this);
    }
    public final void disableButton(final boolean disable) {
        this.counterController.disableButton(disable);
    }
    public final void setTotalTime(final String event) {
        this.eventName = event;
        this.totalTimeController.setTotalTime(event);
        this.counterController.setStarter(event, this.localTime);
        this.localTime = LocalTime.MIDNIGHT;
    }
    public final void setStarterValue(LocalTime localTime) {
        this.localTime = localTime;
        this.setTotalTime(eventName);
    }
    @Override
    public final View getView() {
        return new GeneralCounterView(List.of(List.of(this.eventCounterController.getView().getRoot(),
                this.totalTimeController.getView().getRoot()), List.of(this.counterController.getView().getRoot())));
    }
}
