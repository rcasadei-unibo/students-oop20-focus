package oop.focus.calendar.controller;


import javafx.stage.Stage;
import oop.focus.calendar.model.Format;
import oop.focus.calendar.view.CalendarSettingsView;
import oop.focus.calendar.view.CalendarSettingsViewImpl;
import oop.focus.common.View;


public class CalendarSettingsControllerImpl implements CalendarSettingsController {

    //Classes
    private final CalendarMonthController monthcontroller;
    private final CalendarSettingsView settingsview;

    //Variables
    private Format format;
    private double spacing;

    //Costants
    private static final double SPACING = 50; 
    private static final double MINSPACING = 30;

    public CalendarSettingsControllerImpl(final CalendarMonthController monthcontroller) {
        this.monthcontroller = monthcontroller;

        settingsview = new CalendarSettingsViewImpl(this);
        this.format = Format.NORMAL;
        this.spacing = SPACING;
    }


    public final void setFormat(final Format format) {
        this.format = format;
    }


    public final Format getFormat() {
        return this.format;
    }


    public final boolean checkSpacing(final String spacing) {
        if (spacing.isBlank()) {
            this.setSpacing(SPACING);
            return true;
        } else {
            try {
                final double d = Double.parseDouble(spacing);
                if (d < MINSPACING) {
                    this.settingsview.windowsError("minimo valore concesso e' 30");
                    return false;
                }  else {
                    this.setSpacing(d);
                    return true;
                } 
            } catch (NumberFormatException nfe) {
                this.settingsview.windowsError("inserire dei numeri");
                return false;
            }
        }

    }


    public final void setSpacing(final double spacing) {
        this.spacing = spacing;
    }


    public final double getSpacing() {
        return this.spacing;
    }


    public final void updateView() {
        monthcontroller.setFormat(this.format);
        monthcontroller.setSpacing(this.spacing);
        monthcontroller.updateView();
    }

    public final void setWindow(final Stage stage) {
        settingsview.setWindow(stage);
    }

    public final View getView() {
        return settingsview;
    }

}
