package oop.focus.finance.view;

import javafx.scene.control.Button;
import oop.focus.finance.controller.BaseController;

public interface FinanceMenuButton {

    /**
     * @return the Button of FinanceMenuButton
     */
    Button getButton();

    /**
     * Returns the action to be performed on the controller when the button is clicked.
     *
     * @param controller that contains the method for the action
     */
    void getAction(BaseController controller);
}
