package oop.focus.finance.view.tiles;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import oop.focus.finance.controller.FXMLPaths;
import oop.focus.finance.model.Transaction;
import oop.focus.finance.view.bases.GenericView;

import java.text.DecimalFormat;

public class TransactionViewImpl extends GenericView<Transaction> implements Initializable, TransactionView {

    @FXML
    private Label descriptionLabel, categoryLabel, colorLabel, dateLabel, amountLabel;

    public TransactionViewImpl(final Transaction transaction) {
        super(transaction, FXMLPaths.MOVTILE);
    }

    @Override
    public final void populate() {
        this.descriptionLabel.setText(super.getX().getDescription());
        this.categoryLabel.setText(super.getX().getCategory().getName());
        this.colorLabel.setTextFill(Color.valueOf(super.getX().getCategory().getColor()));
        this.dateLabel.setText(super.getX().getDateToString());
        DecimalFormat df = new DecimalFormat("#0.00");
        this.amountLabel.setText("" + df.format((double) super.getX().getAmount() / 100));
    }

    @Override
    public final Transaction getTransaction() {
        return super.getX();
    }
}
