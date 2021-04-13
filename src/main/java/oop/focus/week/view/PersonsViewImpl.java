package oop.focus.week.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import oop.focus.week.controller.FXMLPaths;
import oop.focus.week.controller.PersonsController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.AnchorPane;
import oop.focus.homepage.model.Person;
import oop.focus.homepage.model.PersonImpl;
import oop.focus.week.controller.RelationshipsController;
import oop.focus.week.controller.RelationshipsControllerImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PersonsViewImpl implements PersonsView {

    @FXML
    private AnchorPane panePersons;

    @FXML
    private TableView<Person> tableViewPersons;

    @FXML
    private TableColumn<Person, String> name;

    @FXML
    private TableColumn<Person, String> relationship;

    @FXML
    private Button delete;

    @FXML
    private Button add;

    @FXML
    private Button degreeOfKinsip;

    private final PersonsController controller;
    private Node root;

    public PersonsViewImpl(final PersonsController controller) {
        this.controller = controller;

        final FXMLLoader loader = new FXMLLoader(this.getClass().getResource(FXMLPaths.PERSONS.getPath()));
        loader.setController(this);

        try {
            this.root = loader.load();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void setProperty() {
        this.tableViewPersons.prefWidthProperty().bind(this.panePersons.widthProperty().multiply(0.50));
        this.tableViewPersons.prefHeightProperty().bind(this.panePersons.heightProperty().multiply(0.8));
        this.name.prefWidthProperty().bind(this.tableViewPersons.widthProperty().divide(2));
        this.relationship.prefWidthProperty().bind(this.tableViewPersons.widthProperty().divide(2));
    }

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        this.setProperty();
        this.populateTableView();
        this.setButtonOnAction();
    }

    private final void setButtonOnAction() {
        this.add.setOnAction(event -> this.add(event));
        this.delete.setOnAction(event -> this.delete(event));
        this.degreeOfKinsip.setOnAction(event -> {
            try {
                this.goToDegree(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public final void populateTableView(){
        name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override
            public void handle(final CellEditEvent<Person, String> event) {
                final Person person = event.getRowValue();
                person.setName(event.getNewValue());
            }
        });

        relationship.setCellValueFactory(new PropertyValueFactory<Person, String>("relationships"));
        relationship.setCellFactory(TextFieldTableCell.forTableColumn());
        relationship.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override
            public void handle(final CellEditEvent<Person, String> event) {
                final Person person = event.getRowValue();
                person.setRelationships(event.getNewValue());
            }
        });

        this.tableViewPersons.setEditable(false);
        this.tableViewPersons.getItems().clear();
        this.tableViewPersons.setItems(this.controller.getPersons());
    }

    public final void goToDegree(final ActionEvent event) throws IOException {
        final RelationshipsController relationshipsController = new RelationshipsControllerImpl(this.controller.getDsi());
        this.panePersons.getChildren().clear();
        this.panePersons.getChildren().add(relationshipsController.getView().getRoot());
    }

    public final void delete(final ActionEvent event) {
        final String name = this.tableViewPersons.getSelectionModel().getSelectedItem().getName();
        final String degree = tableViewPersons.getSelectionModel().getSelectedItem().getRelationships();
        this.controller.deletePerson(new PersonImpl(name, degree));
        this.refreshTableView();
    }

    public final void refreshTableView() {
        this.tableViewPersons.getItems().removeAll(tableViewPersons.getSelectionModel().getSelectedItems());
    }

    public final void add(final ActionEvent event) {
        final AddNewPersonView newPerson = new AddNewPersonView(this.controller, this);
        final Stage stage = new Stage();
        stage.setScene(new Scene((Parent) newPerson.getRoot()));
        stage.show();
    }

    @Override
    public final Node getRoot() {
        return this.root;
    }
}
