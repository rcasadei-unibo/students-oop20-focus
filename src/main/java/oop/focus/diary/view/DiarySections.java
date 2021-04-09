package oop.focus.diary.view;

import javafx.geometry.Dimension2D;
import javafx.scene.Parent;

public enum DiarySections {
    DIARY("Diario", "", new BaseDiary(new Dimension2D(1000, 400)).getRoot()),
    STOPWATCH("Cronometro", "", new StopwatchView(new Dimension2D(1000, 400)).getRoot()),
    TIMER("Timer","", new TimerView(new Dimension2D(1000, 400)).getRoot());
    private final String name;
    private String style;
    private Parent view;
    DiarySections(String name, String style, Parent view) {
        this.name = name;
        this.style = style;
        this.view = view;
    }
    public String getName() {
        return this.name;
    }

    public String getStyle() {
        return this.style;
    }

    public Parent getView() {
        return this.view;
    }
}
