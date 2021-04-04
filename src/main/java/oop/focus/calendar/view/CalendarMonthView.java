package oop.focus.calendar.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import oop.focus.calendar.controller.CalendarMonthController;

public interface CalendarMonthView {


    /**
     * Used for get the controller of the month.
     * @return controller of the month views
     */
    CalendarMonthController getController();


    /**
     * Used for get the month info label (years and name).
     * @return label
     */
    Label getMonthInfo();


    /**
     * Used for set the month view.
     * @param month view
     */
    void setMonthView(VBox month);


    /**
     * Used for get the month view box.
     * @return vbox
     */
    VBox getMonthView();

}
