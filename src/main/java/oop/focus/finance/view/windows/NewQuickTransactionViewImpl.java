package oop.focus.finance.view.windows;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oop.focus.common.Repetition;
import oop.focus.finance.controller.FXMLPaths;
import oop.focus.finance.controller.NewCategoryController;
import oop.focus.finance.controller.NewCategoryControllerImpl;
import oop.focus.finance.controller.NewQuickTransactionController;
import oop.focus.finance.model.Account;
import oop.focus.finance.model.Category;

public class NewQuickTransactionViewImpl extends GenericWindow<NewQuickTransactionController> {

    @FXML
    private Label titleLabel, repetitionLabel, dateLabel;
    @FXML
    private TextField descriptionTextField, amountTextField, hoursTextField, minutesTextField;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private ChoiceBox<Category> categoryChoice;
    @FXML
    private ChoiceBox<Account> accountChoice;
    @FXML
    private ChoiceBox<Repetition> repetitionChioce;
    @FXML
    private ChoiceBox<String> typeChoice;
    @FXML
    private Button cancelButton, saveButton, newCategoryButton;

    public NewQuickTransactionViewImpl(final NewQuickTransactionController controller) {
        super(controller, FXMLPaths.NEWMOVEMENT);
    }

    @Override
    public final void populate() {
        this.titleLabel.setText("NUOVA TRANSAZIONE RAPIDA");
        this.dateLabel.setVisible(false);
        this.dataPicker.setVisible(false);
        this.repetitionLabel.setVisible(false);
        this.repetitionChioce.setVisible(false);
        this.hoursTextField.setVisible(false);
        this.minutesTextField.setVisible(false);
        this.categoryChoice.setItems(super.getX().getCategories());
        this.accountChoice.setItems(super.getX().getAccounts());
        this.typeChoice.setItems(FXCollections.observableArrayList("Entrata", "Uscita"));
        this.typeChoice.setValue("Uscita");
        this.cancelButton.setOnAction(event -> this.close());
        this.saveButton.setOnAction(event -> this.save());
        this.newCategoryButton.setOnAction(event -> this.showNewCategory());
    }

    private void showNewCategory() {
        final NewCategoryController controller = new NewCategoryControllerImpl(super.getX().getManager());
        final Stage stage = new Stage();
        stage.setScene(new Scene((Parent) controller.getView().getRoot()));
        stage.show();
    }

    @Override
    public final void save() {
        if (this.descriptionTextField.getText().isEmpty() || isNotNumeric(this.amountTextField.getText())
                || this.categoryChoice.getValue() == null || this.accountChoice.getValue() == null
                || this.typeChoice.getValue() == null || Double.parseDouble(this.amountTextField.getText()) <= 0) {
            super.allert("I campi non sono stati compilati correttamente.");
        } else {
            super.getX().newQuickTransaction(this.descriptionTextField.getText(),
                    Double.parseDouble(this.amountTextField.getText()) * (this.typeChoice.getValue().equals("Uscita") ? -1 : 1),
                    this.categoryChoice.getValue(), this.accountChoice.getValue());
            this.close();
        }
    }
}
