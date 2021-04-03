package oop.focus.diary.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import oop.focus.diary.controller.ControllerFactoryImpl;
import oop.focus.diary.controller.ControllersFactory;
import oop.focus.diary.controller.TotalTimeControllerImpl;
import oop.focus.diary.controller.CounterControllerImpl;
import oop.focus.diary.controller.UpdateView;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.net.URL;
import java.util.ResourceBundle;

public class StopwatchView implements  Initializable {
    private static final  DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH : mm : ss");

    @FXML
    private Label nameEventLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private ComboBox<String> chooseEvent;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Label counterLabel;


    private final ControllersFactory factory;

    public StopwatchView() {
        this.factory = new ControllerFactoryImpl(); }


    public final void initialize(final URL location, final ResourceBundle resources) {
        final TotalTimeControllerImpl controller = this.factory.createCounterController();
        final CounterControllerImpl specificController = this.factory.createStopwatch();
        final UpdateView connection = new UpdateView(specificController, this.counterLabel);
        this.chooseEvent.getItems().addAll(controller.getAllEvents());
        this.nameEventLabel.setText("Inserisci evento");
        this.startButton.setText("Start");
        this.startButton.setDisable(true);
        this.stopButton.setText("Stop");
        this.stopButton.setDisable(true);
        this.counterLabel.setText(LocalTime.MIDNIGHT.toString(TIME_FORMATTER));
        this.chooseEvent.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.timeLabel.setText(controller.getTotalTime(newValue).toString(TIME_FORMATTER));
            specificController.setStarter(newValue, LocalTime.MIDNIGHT);
            this.startButton.setDisable(false);
            }
        );
        CommonView.addListener(specificController, connection, this.startButton, this.stopButton, this.chooseEvent);
        this.stopButton.setOnMouseClicked(event -> {
            CommonView.addStopTimer(controller, specificController, this.startButton, this.stopButton, this.timeLabel, this.chooseEvent, TIME_FORMATTER);
            this.chooseEvent.setDisable(false);
        });

    }

}
