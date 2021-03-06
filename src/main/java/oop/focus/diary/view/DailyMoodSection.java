package oop.focus.diary.view;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import oop.focus.common.View;
import oop.focus.db.exceptions.DaoAccessException;
import oop.focus.diary.controller.DailyMoodController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link View}. Daily mood section represents the section of daily mood,
 * filling and returning a pane with the different mood, which can be chosen by the user.
 */
public class DailyMoodSection implements View {
    private static final Rectangle2D SCREEN_BOUNDS = Screen.getPrimary().getBounds();
    private static final double NUM_ICONS = 5;
    private static final double DARK_ICONS = -0.4;
    private static final double LIGHT_ICONS = 0.0;
    private static final double BUTTON_WIDTH = 0.3;
    private static final double BUTTON_HEIGHT = 0.15;
    private static final double V_BOX_HEIGHT = 0.5;
    private static final double V_BOX_WIDTH = 0.5;
    private static final int GRID_GAP = 5;
    private static final double GRID_HEIGHT = 0.3;
    private static final double LABEL_HEIGHT = 0.2;
    private final VBox vBox;
    private final Button button;
    private final Map<Button, Integer> map;
    private final Label dailyMoodLabel;
    private final DailyMoodController controller;
    private final GridPane gridPane;

    /**
     * Initializes a new daily mood section.
     * @param controller    the daily mood controller
     */
    public DailyMoodSection(final DailyMoodController controller) {
        this.gridPane = new GridPane();
        this.map = new HashMap<>();
        this.dailyMoodLabel = new Label("Come ti senti oggi?");
        this.controller = controller;
        this.button = new Button("Modifica");
        this.setGrid();
        this.setBrightness();
        this.map.keySet().forEach(s -> s.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(final MouseEvent event) {
                try {
                    DailyMoodSection.this.controller.addDailyMood(DailyMoodSection.this.map.get(s));
                    DailyMoodSection.this.setBrightness();
                } catch (final DaoAccessException e) {
                    e.printStackTrace();
                }
            }
        }));
        this.button.setOnMouseClicked(event -> {
            try {
                this.controller.removeChoice();
                this.setBrightness();
            } catch (final DaoAccessException e) {
                e.printStackTrace();
            }
        });
        this.vBox = (VBox) new ContainerFactoryImpl().mergeVertically(List.of(this.dailyMoodLabel, this.gridPane,
                this.button)).getRoot();
    }

    /**
     * Sets the configuration and fill a {@link GridPane} with different {@link Button}. It also sets the
     * graphic of each button taking it from the appropriate class {@link DailyMoodView}.
     */
    private void setGrid() {
        int index = 0;
        this.gridPane.setVgap(GRID_GAP);
        this.gridPane.setHgap(GRID_GAP);
        this.gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < NUM_ICONS; i++) {
            final DailyMoodView view = new DailyMoodView(i);
            final Button b = new Button();
            this.map.put(b, i);
            b.setGraphic(view.getRoot());
            b.setStyle("-fx-border-color: transparent ; -fx-background-color: transparent");
            this.gridPane.add(b, index, 0);
            index++;
        }

    }

    /**
     * The method sets the brightness of each button of grid. In that way, when a button is clicked, unselected buttons
     * darken.
     */
    private void setBrightness() {
        for (final var elem : this.map.keySet()) {
            final ColorAdjust blackout = new ColorAdjust();
            if (this.controller.getValueChosen().isPresent()) {
                if (!this.map.get(elem).equals(this.controller.getValueChosen().get())) {
                    elem.setDisable(true);
                    blackout.setBrightness(DARK_ICONS);
                }
            } else  {
                elem.setDisable(false);
                blackout.setBrightness(LIGHT_ICONS);
            }
                elem.setEffect(blackout);
            }
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getRoot() {
        this.gridPane.prefWidthProperty().bind(this.vBox.widthProperty());
        this.vBox.prefWidthProperty().set(SCREEN_BOUNDS.getWidth() * V_BOX_WIDTH);
        this.vBox.prefHeightProperty().set(SCREEN_BOUNDS.getHeight() * V_BOX_HEIGHT);
        this.dailyMoodLabel.prefWidthProperty().bind(this.vBox.widthProperty());
        this.dailyMoodLabel.prefHeightProperty().bind(this.vBox.heightProperty().multiply(LABEL_HEIGHT));
        this.dailyMoodLabel.setAlignment(Pos.CENTER);
        this.gridPane.prefHeightProperty().bind(this.vBox.heightProperty().multiply(GRID_HEIGHT));
        this.button.prefWidthProperty().bind(this.vBox.widthProperty().multiply(BUTTON_WIDTH));
        this.button.prefHeightProperty().bind(this.vBox.heightProperty().multiply(BUTTON_HEIGHT));
        this.vBox.setAlignment(Pos.CENTER);
        return this.vBox;
    }
}
