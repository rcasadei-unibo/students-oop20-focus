package oop.focus.calendar.month.view;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.Objects.nonNull;
import org.joda.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import oop.focus.calendar.day.controller.CalendarDayController;
import oop.focus.calendar.day.controller.CalendarDayControllerImpl;
import oop.focus.calendar.model.CalendarType;
import oop.focus.calendar.model.Day;
import oop.focus.calendar.month.controller.CalendarMonthController;
import oop.focus.diary.controller.DailyMoodControllerImpl;
import oop.focus.diary.model.DailyMoodManagerImpl;
import oop.focus.diary.view.DailyMoodViewImpl;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;




public class CalendarMonthViewImpl implements CalendarMonthView {

    //Classes
    private final CalendarMonthController monthController;

    //View
    private final Label monthInfo;
    private Stage dayWindows;
    private final VBox monthBox;

    //Variables
    private int counter;     // count the days in a row
    private int count;     // count the rows
    private final CalendarType type;

    //Lists
    private Map<Button, Scene> cells;

    //Costants
    private static final int BORDER = 20;
    private static final int TABLE_DAYS = 7;
    private static final int GAP = 10;
    private static final int DIM = 200;
    private static final double MOOD_FONT = 1.5;
    private static final double DAY_WIDTH = 200;
    private static final double DAY_HEIGHT = 500;
    private static final double CORNER_RADIUS = 10;

    /**
     * Used for Initialize the month view.
     * @param type : type of calendar to build
     * @param monthcontroller : controller of the month
     */
    public CalendarMonthViewImpl(final CalendarType type, final CalendarMonthController monthcontroller) {
        this.type = type;
        cells  = new HashMap<>();
        this.monthInfo = new Label();
        this.monthController = monthcontroller;
        this.monthBox = buildMonthView();
    }




    /**
     * Build the calendar month view.
     * @return VBox
     */
    private VBox buildMonthView() {


        final VBox container = new VBox();

        container.setAlignment(Pos.CENTER);


        container.getChildren().add(buildTopPanel(container));

        container.getChildren().add(buildGridMonth());

        container.setPadding(new Insets(BORDER, BORDER, BORDER, BORDER));

        container.autosize();

        return container;

    }

    /**
     * Used for build the grid with the days of the month.
     * @return grid    Grid with the days
     */
    private GridPane buildGridMonth() {

        final GridPane daysGrid = new GridPane();


        final Day firstDay = monthController.getMonth().get(0);

        counter = 0;
        count = 0;

        //used for create the first row with the name of the days
        for (int i = 0; i < dayNameLabel().size(); i++) {
            daysGrid.add(dayNameLabel().get(i), i + 1, 0);
            counter++;
        }

        // count the days in a row
        count++;
        // count the rows
        counter = 0;

        //used for put the button of the day in the correct position
        for (int i = 1; i < firstDay.getDayOfTheWeek(); i++) {
            final Button jb = new Button();
            jb.setVisible(false);
            jb.setPrefSize(DIM, DIM);
            daysGrid.add(jb, i, count);
            counter++;
        }


        //build the month grid
        monthController.getMonth().forEach(day -> {

            if (counter % TABLE_DAYS == 0) {
                counter = 0;
                count++;
            }
            counter++;

            if (type.equals(CalendarType.NORMAL)) {
                normalCalendar(daysGrid, day);
            } else if (type.equals(CalendarType.DIARY)) {
                try {
                    diaryCalendar(daysGrid, day);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (type.equals(CalendarType.HOMEPAGE)) {
                homepageCalendar(daysGrid, day);
            }
        });

        daysGrid.setAlignment(Pos.CENTER);
        daysGrid.setHgap(GAP);
        daysGrid.setVgap(GAP);
        return daysGrid;
    }

    /**
     * Used for build a normal calendar.
     * It is made up of buttons that, when clicked,
     * open a window with the information of the day
     * @param daysGrid : grid where put the day
     * @param day : the day from where start to build the calendar
     */
    private void normalCalendar(final GridPane daysGrid, final Day day) {
        final Button jb = new Button(" " + day.getNumber() + " ");
        jb.setFont(Font.font(monthController.getFontSize()));
        jb.setAlignment(Pos.CENTER);
        jb.setOnAction(getDayView());
        jb.setPrefSize(DIM, DIM);
        jb.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(CORNER_RADIUS), BorderWidths.DEFAULT)));
        if (!day.getEvents().isEmpty()) {
            jb.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, new CornerRadii(CORNER_RADIUS), Insets.EMPTY)));
        } else {
            jb.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(CORNER_RADIUS), Insets.EMPTY)));
        }
        final CalendarDayController dayController = new CalendarDayControllerImpl(day, DAY_WIDTH, DAY_HEIGHT);
        dayController.buildDay();
        monthController.configureDay(dayController);
        final ScrollPane dayPane = new ScrollPane(dayController.getView().getRoot());
        dayPane.setFitToWidth(true);
        cells.put(jb, new Scene(dayPane, dayController.getWidth(), dayController.getHeight()));
        daysGrid.add(jb, counter, count);
    }

    /**
     * Used for build an Homepage calendar.
     * Is composed only with label with the number of the day.
     * @param daysGrid : grid where put the day
     * @param day : the day from where start to build the calendar
     */
    private void homepageCalendar(final GridPane daysGrid, final Day day) {
        final Label jb = new Label(" " + day.getNumber() + " ");
        jb.setFont(Font.font(monthController.getFontSize()));
        jb.setAlignment(Pos.CENTER);
        jb.setPrefSize(DIM, DIM);
        jb.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(CORNER_RADIUS), BorderWidths.DEFAULT)));
        if (!day.getEvents().isEmpty()) {
            jb.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, new CornerRadii(CORNER_RADIUS), Insets.EMPTY)));
        } else {
            jb.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(CORNER_RADIUS), Insets.EMPTY)));
        }
        daysGrid.add(jb, counter, count);
    }

    /**
     * Used for build an Diary calendar.
     * Is composed only with label with the number of the day
     * and an icon that represent you daily humor.
     * @param daysGrid : grid where put the day
     * @param day : the day from where start to build the calendar
     */
    private void diaryCalendar(final GridPane daysGrid, final Day day) throws IOException {
        final VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setPrefSize(DIM, DIM);
        final Label dayNumber = new Label(" " + day.getNumber() + " ");
        dayNumber.setFont(Font.font(monthController.getFontSize() / MOOD_FONT));
        container.getChildren().add(dayNumber);
        final LocalDate localDay = new LocalDate(day.getYear(), day.getMonthNumber(), day.getNumber());
        final DailyMoodControllerImpl moodcontroller = new DailyMoodControllerImpl(new DailyMoodManagerImpl(monthController.getDataSource()));
        if (moodcontroller.getValueByDate(localDay).isPresent()) {
            final Optional<Integer> index = moodcontroller.getValueByDate(localDay);
            if (index.isPresent()) {
                //jb.fitWidthProperty().bind(container.widthProperty().divide(2));
                //jb.fitHeightProperty().bind(container.heightProperty().divide(2));
                container.getChildren().add(new DailyMoodViewImpl(index.get()).getRoot());
            }
        }
        daysGrid.add(container, counter, count);
    }

    /**
     * used for create the row of the days names.
     * @return list : dayNameLabel
     */
    private List<Label> dayNameLabel() {


        final List<Label> daysName = new ArrayList<>();

        final Label lunedi = new Label("lun");
        lunedi.setFont(Font.font(monthController.getFontSize()));
        final Label martedi = new Label("mar");
        martedi.setFont(Font.font(monthController.getFontSize()));
        final Label mercoledi = new Label("mer");
        mercoledi.setFont(Font.font(monthController.getFontSize()));
        final Label giovedi = new Label("gio");
        giovedi.setFont(Font.font(monthController.getFontSize()));
        final Label venerdi = new Label("ven");
        venerdi.setFont(Font.font(monthController.getFontSize()));
        final Label sabato = new Label("sab");
        sabato.setFont(Font.font(monthController.getFontSize()));
        final Label domenica = new Label("dom");
        domenica.setFont(Font.font(monthController.getFontSize()));

        daysName.add(lunedi);
        daysName.add(martedi);
        daysName.add(mercoledi);
        daysName.add(giovedi);
        daysName.add(venerdi);
        daysName.add(sabato);
        daysName.add(domenica);

        daysName.forEach(e -> e.setPrefSize(DIM, DIM));
        daysName.forEach(e -> e.setAlignment(Pos.CENTER));

        return daysName;
    }




    /**
     * Used for build the top panel of the month view.
     * Composed by the change month button and a label where are
     * written the year and the month.
     * @param container : is the place where the box will be.
     */
    private HBox buildTopPanel(final VBox container) {
        final HBox topPanel = new HBox();
        this.monthInfo.setText(monthController.getMonth().get(0).getYear() + "   " + monthController.getMonth().get(0).getMonth());
        this.monthInfo.setFont(Font.font(monthController.getFontSize()));
        this.monthInfo.setAlignment(Pos.CENTER);

        final Button next = new Button("prossimo");
        final Button previous = new Button("precedente");
        next.setOnAction(changeMonthButton(this, false));
        previous.setOnAction(changeMonthButton(this, true));

        topPanel.getChildren().add(previous);
        topPanel.getChildren().add(monthInfo);
        topPanel.getChildren().add(next);
        topPanel.setAlignment(Pos.CENTER);
        topPanel.prefWidthProperty().bind(container.widthProperty());
        topPanel.setSpacing(BORDER);
        return topPanel;
    }

    /**
     * Used for launch a windows with the day view of the clicked one.
     * @return EventHandler
     */
    private EventHandler<ActionEvent> getDayView() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                final Button bt = (Button) event.getSource();
                final Scene dayCheck = cells.get(bt);
                if (dayWindows == null) {

                    dayWindows = new Stage();
                    final Scene p = cells.get(bt);

                    dayWindows.setScene(p);

                } else if (!dayWindows.getScene().equals(dayCheck)) {

                    dayWindows.close();
                    dayWindows = new Stage();
                    final Scene p = cells.get(bt);
                    dayWindows.setScene(p);
                }

                dayWindows.show();

            }

        };
    }


    /**
     * Is an EventHandler for change the month (next or previous one).
     * @param monthView : the month view
     * @param flag : true previous month, false next month
     * @return EventHandler
     */
    private EventHandler<ActionEvent> changeMonthButton(final CalendarMonthView monthView, final Boolean flag) {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                monthController.getCalendarLogic().changeMonth(flag);
                monthController.setMonth();
                updateView(monthView);
            }

        };
    }


    public final void updateView(final CalendarMonthView monthInfo) {
        if (nonNull(dayWindows)) {
            dayWindows.close();
        }
        cells  = new HashMap<>();
        monthController.getCalendarLogic().generateMonth();
        monthController.setMonth();
        this.monthBox.getChildren().remove(this.monthBox.getChildren().size() - 1);
        this.monthBox.getChildren().add(buildGridMonth());
        this.setMonthInfo(this.monthInfo, monthController.getMonth().get(0).getYear() + "   " + monthController.getMonth().get(0).getMonth());
    }

    public final VBox getMonthView() {
        this.monthController.updateView();
        return this.monthBox;
    }


    /**
     * Used for set the month view.
     * @param month : the box that will contain all the object of the month view
     */
    private void setMonthInfo(final Label monthInfo, final String string) {
        monthInfo.setText(string);
    }


    public final Node getRoot() {
        return this.getMonthView();
    }

}