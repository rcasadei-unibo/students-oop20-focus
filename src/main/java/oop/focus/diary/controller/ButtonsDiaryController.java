package oop.focus.diary.controller;

import oop.focus.common.Controller;
import oop.focus.common.UpdatableController;
import oop.focus.common.View;
import oop.focus.db.DataSource;

import oop.focus.diary.view.ButtonsDiaryView;

/**
 * ButtonsDiaryController is the Controller of diary's lateral button.
 */
public class ButtonsDiaryController implements Controller {
    private final View content;

    /**
     * Instantiates a new buttons diary controller and creates the associated view.
     * @param controller    the sectionsController
     * @param dataSource    the{@link DataSource} from which to retrieve data
     */
    public ButtonsDiaryController(final UpdatableController<Controller> controller, final DataSource dataSource) {
        this.content = new ButtonsDiaryView(controller, dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView() {
        return this.content;
    }
}
