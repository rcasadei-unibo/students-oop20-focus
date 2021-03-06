package oop.focus.finance.view.windows;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import oop.focus.finance.controller.FXMLPaths;
import oop.focus.finance.controller.GroupController;
import oop.focus.calendar.persons.model.Person;
import oop.focus.finance.view.StaticAlerts;

import java.util.Optional;

/**
 * Class that implements the detail view of a person.
 */
public class PersonDetailsWindowImpl extends FinanceDetailsWindowImpl {

    @FXML
    private Label titleLabel, descriptionLabel, categoryLabel, dateLabel, accountLabel, amountLabel, subscriptionLabel,
            dataDescriptionLabel, dataCategoryLabel, firstEuroLabel;
    @FXML
    private Button deleteButton;

    private final GroupController controller;
    private final Person person;

    public PersonDetailsWindowImpl(final GroupController controller, final Person person) {
        this.controller = controller;
        this.person = person;
        this.loadFXML(FXMLPaths.TRANSACTIONDETAILS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void populateStaticLabels() {
        this.titleLabel.setText("DETTAGLI DI " + this.person.getName());
        this.descriptionLabel.setText("Nome:");
        this.categoryLabel.setText("Parentela:");
        this.dateLabel.setVisible(false);
        this.accountLabel.setVisible(false);
        this.amountLabel.setVisible(false);
        this.subscriptionLabel.setVisible(false);
        this.firstEuroLabel.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void populateDynamicLabels() {
        this.dataDescriptionLabel.setText(this.person.getName());
        this.dataCategoryLabel.setText(this.person.getRelationships());
    }

    /**
     * {@inheritDoc}
     * If the user confirms this, the person is removed from the group transaction group.
     */
    @Override
    public final void save() {
        final Optional<ButtonType> result = StaticAlerts.confirm("Sicuro di voler elminare " + this.person.getName() + "?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                this.controller.deletePerson(this.person);
            } catch (final IllegalStateException e) {
                StaticAlerts.alert("Non e' possibile eliminare " + this.person.getName() + " perche' ha ancora dei debiti.");
            }
        }
        this.close();
    }
}
