package oop.focus.diary.controller;
import oop.focus.application.controller.SectionsController;
import oop.focus.common.Controller;
import oop.focus.common.UpdatableController;
import oop.focus.common.View;
import oop.focus.db.DataSource;
import oop.focus.diary.view.ContainerFactoryImpl;
import java.util.List;

/**
 * Implementation of {@link Controller}. This class puts in communication the
 *  different Controller of diary.
 */
public class GeneralDiaryController implements Controller {
    private final View content;

    /**
     * Instantiates a new general diary controller and initializes other Controller relatives to diary.
     * @param dataSource    dataSource the {@link DataSource} from which to retrieve data
     */
    public GeneralDiaryController(final DataSource dataSource) {
        final UpdatableController<Controller> controller = new SectionsController();
        final Controller buttonController = new ButtonsDiaryController(controller, dataSource);
        this.content = new ContainerFactoryImpl().mergeHorizontally(List.of(buttonController.getView().getRoot(),
                controller.getView().getRoot()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView() {
        return this.content;
    }
}
