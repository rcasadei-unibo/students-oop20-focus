package oop.focus.diary.view;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * This interface can be used to set the icons in the appropriate grid and the "modify" button.
 */
public interface DailyMoodView {
    /**
     * Returns a grid with all dailyMood's icons presents in the appropriate folder of resource.
     * @return  a grid of icons
     */
    GridPane getGrid();

    /**
     * Returns the "modify" button, which pressed deletes today's daily mood(if present)
     * and allows to select another mood.
     * @return  the "modify" button
     */
    Button getButton();
}