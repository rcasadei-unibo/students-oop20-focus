package oop.focus.finance.view.windows;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import oop.focus.common.Repetition;
import oop.focus.finance.controller.FXMLPaths;
import oop.focus.finance.controller.TransactionsController;
import oop.focus.finance.model.Transaction;
import oop.focus.finance.view.StaticAlerts;
import oop.focus.finance.view.StaticFormats;

import java.util.Optional;

/**
 * Class that implements the detail view of a transaction.
 */
public class TransactionDetailsWindowImpl extends FinanceDetailsWindowImpl {

    @FXML
    private Label dataDescriptionLabel, dataCategoryLabel, dataDateLabel,
            dataAccountLabel, dataAmountLabel, dataSubscriptionLabel;

    private final TransactionsController controller;
    private final Transaction transaction;

    public TransactionDetailsWindowImpl(final TransactionsController controller, final Transaction transaction) {
        this.controller = controller;
        this.transaction = transaction;
        this.loadFXML(FXMLPaths.TRANSACTIONDETAILS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void populateDynamicLabels() {
        this.dataDescriptionLabel.setText(this.transaction.getDescription());
        this.dataCategoryLabel.setText(this.transaction.getCategory().getName());
        this.dataDateLabel.setText(StaticFormats.formatDate(this.transaction.getDate()));
        this.dataAccountLabel.setText(this.transaction.getAccount().getName());
        this.dataAmountLabel.setText(StaticFormats.formatAmount((double) this.transaction.getAmount() / 100));
        this.dataAmountLabel.setTextFill(this.transaction.getAmount() > 0 ? Color.GREEN : Color.RED);
        this.dataSubscriptionLabel.setText(this.transaction.getRepetition().equals(Repetition.ONCE) ? "No"
                : this.transaction.getRepetition().getName());
    }

    /**
     * {@inheritDoc}
     * If the user confirms this, the transaction is deleted.
     */
    @Override
    public final void save() {
        final Optional<ButtonType> result = StaticAlerts.confirm("Sicuro di voler elminare la transazione?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.controller.deleteTransaction(this.transaction);
        }
        this.close();
    }
}
