package oop.focus.homepage.controller;

import oop.focus.common.View;
import oop.focus.db.DataSource;
import oop.focus.diary.view.ContainerFactoryImpl;
import oop.focus.finance.controller.FinanceHomePageController;
import oop.focus.finance.controller.FinanceHomePageControllerImpl;
import oop.focus.finance.model.FinanceManager;

import java.util.List;

public class GeneralHomePageControllerImpl implements GeneralHomePageController {

    private final HomePageController calendarHomePage;
    private final FinanceHomePageController financheHomePage;

    public GeneralHomePageControllerImpl(final DataSource dsi, final FinanceManager financeManager) {
        this.calendarHomePage = new HomePageControllerImpl(dsi);
        this.financheHomePage = new FinanceHomePageControllerImpl(financeManager);
    }

    public final View getView() {
        return new ContainerFactoryImpl().mergeHorizontally(List.of(this.calendarHomePage.getView().getRoot(), this.financheHomePage.getView().getRoot()));
    }

    @Override
    public final HomePageController getHomePageController() {
        return this.calendarHomePage;
    }
}
