package oop.focus.calendar.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop.focus.calendar.controller.CalendarController;
import oop.focus.calendar.settings.view.CalendarSettingsView;
import oop.focus.common.View;

public class CalendarViewImpl implements View {

    //Classes
    private final CalendarController calendarController;

    //View
    private final HBox calendarPage;

    //Costants
    private static final double WIDTH_BUTTON_PANEL = 0.2; 
    private static final double WIDTH_PANEL = 0.8;
    private static final double WIDTH_BUTTON = 0.7;
    private static final double GAP = 20;
    private static final double SETTING_WIDTH = 300;
    private static final double SETTING_HEIGHT = 150;
    private static final double EVENT_WIDTH = 700;
    private static final double EVENT_HEIGHT = 600;


    public CalendarViewImpl(final CalendarController calendarController) {
        this.calendarController = calendarController;
        this.calendarPage = new HBox();
        this.buildCalendarPage();
    }

    /**
     * Used for build the calendar page box.
     */
    private void buildCalendarPage() {

        final VBox buttonColumn = new VBox();
        final VBox panelColumn = new VBox();

        this.calendarPage.getChildren().add(buttonColumn);
        this.calendarPage.getChildren().add(panelColumn);


        this.configureColumn(buttonColumn, WIDTH_BUTTON_PANEL);
        this.configureColumn(panelColumn, WIDTH_PANEL);


        this.columnButton(buttonColumn, "Mese", this.addPanel(panelColumn, this.calendarController.getMonthController().getView().getRoot()));
        this.columnButton(buttonColumn, "Settimana", this.addPanel(panelColumn, this.calendarController.getWeekController().getView().getRoot()));
        this.columnButton(buttonColumn, "Persone", this.addPanel(panelColumn, this.calendarController.getPersonController().getView().getRoot()));
        this.columnButton(buttonColumn, "Statistiche", this.addPanel(panelColumn, this.calendarController.getStatisticsController().getView().getRoot()));
        this.buildButtonWindows(buttonColumn, "Impostazioni", this.calendarController.getSettingsController().getView(), SETTING_WIDTH, SETTING_HEIGHT);
        this.buildButtonWindows(buttonColumn, "Aggiungi Evento", this.calendarController.getNewEventController().getView(), EVENT_WIDTH, EVENT_HEIGHT);
        this.buildButtonWindows(buttonColumn, "Info Eventi", this.calendarController.getEventInfoController().getView(), EVENT_WIDTH, EVENT_HEIGHT);
        panelColumn.getChildren().add(this.calendarController.getMonthController().getView().getRoot());
    }


    /**
     * Used for configure the columns box.
     * @param column : column box to configure
     */
    private void configureColumn(final VBox column, final double width) {
        column.prefWidthProperty().bind(this.calendarPage.widthProperty().multiply(width));
        column.prefHeightProperty().bind(this.calendarPage.heightProperty());
        column.setAlignment(Pos.CENTER);
        if (width == WIDTH_BUTTON_PANEL) {
        column.setSpacing(GAP);
        }

    }


    /**
     * Used for create the button to put in the button column (the left one of the view).
     * @param buttonColumn : where the button will be
     * @param string : name of the button
     * @param openThisPanel : is the EventHandler that open a panel
     */
    private void columnButton(final VBox buttonColumn, final String string, final EventHandler<ActionEvent> openThisPanel) {
        final Button button = new Button(string);
        button.getStyleClass().add("lateral-button");
        button.setPrefHeight(GAP * 2);
        button.setAlignment(Pos.CENTER);
        button.prefWidthProperty().bind(buttonColumn.widthProperty().multiply(WIDTH_BUTTON));
        buttonColumn.getChildren().add(button);
        button.setOnAction(openThisPanel);
    }

    /**
     * Used for build the button for open window.
     * @param buttonColumn : column where the button will be
     * @param name : String with the name of the button
     * @param view : {@link View} of the windows to open
     * @param width : width of the window
     * @param height : height of the window
     */

    private void buildButtonWindows(final VBox buttonColumn, final String name, final View view, final double width, final double height) {
        final Button button = new Button(name);
        button.getStyleClass().add("lateral-button");
        button.prefWidthProperty().bind(buttonColumn.widthProperty().multiply(WIDTH_BUTTON));
        button.setPrefHeight(GAP * 2);
        button.setAlignment(Pos.CENTER);
        final Stage stage = new Stage();
        stage.setScene(new Scene((Parent) view.getRoot(), width, height));
        if ("Impostazioni".equalsIgnoreCase(name)) {
            ((CalendarSettingsView) this.calendarController.getSettingsController().getView()).setWindow(stage);
        }
        button.setOnAction((e) -> stage.show());
        buttonColumn.getChildren().add(button);
    }

    /**
     * Used for show the panel of the button that we have clicked.
     * @param panelColumn : column where we put the panel
     * @param root : root of the panel
     * @return EventHandler
     */
    private EventHandler<ActionEvent> addPanel(final VBox panelColumn, final Node root) {
        return event -> {
            if (!panelColumn.getChildren().isEmpty()) {
                panelColumn.getChildren().remove(0);
            }
            panelColumn.getChildren().add(root);
        };
    }

    /**
     * {@inheritDoc}
     */
    public final Node getRoot() {
        return this.calendarPage;
    }

}
