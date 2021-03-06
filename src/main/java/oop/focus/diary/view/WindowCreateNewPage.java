package oop.focus.diary.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oop.focus.common.View;
import oop.focus.diary.controller.DiaryPagesController;
import oop.focus.diary.controller.FXMLPaths;

/**
 * This class represents a new window, opened to add new page in diary's section. When new page is created,
 * his title and his content are chosen by the user.
 */
public class WindowCreateNewPage implements Initializable, View {

    @FXML
    private Label insertTitleLabel;

    @FXML
    private TextField titleDiaryPage;

    @FXML
    private TextArea content;

    @FXML
    private Button create;
    private Parent root;
    private final DiaryPagesController controller;

    /**
     *  Instantiates a new window create new page and opens the relative FXML file.
     * @param controller    diaryPagesController
     */
    public WindowCreateNewPage(final DiaryPagesController controller) {
        this.controller = controller;
        final FXMLLoader loader = new FXMLLoader(this.getClass().getResource(FXMLPaths.INSERT_DIARY_PAGE.getPath()));
        loader.setController(this);
        try {
            this.root = loader.load();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts new diary Page, calling the specific method of {@link DiaryPagesController}.
     *  Before doing this, check that there are no special characters.
      */
    private void createNewPage() {
        if (!this.titleDiaryPage.getText().isEmpty() && !this.content.getText().isEmpty()) {
            final Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
            final Matcher m = p.matcher(this.titleDiaryPage.getText());
            if (m.find()) {
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setContentText("Il titolo della pagina di diario non deve avere caratteri speciali");
                alert.showAndWait();
            } else {
                this.controller.createPage(this.titleDiaryPage.getText(), this.content.getText());
                final Stage stage = (Stage) this.content.getScene().getWindow();
                stage.close();
            }
        } else {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setContentText("Inserire nome e contenuto della pagina di diario");
            alert.showAndWait();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.insertTitleLabel.setText("Titolo");
        this.create.setText("Crea");
        this.create.setOnMouseClicked(event -> this.createNewPage());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Node getRoot() {
        return this.root;
    }
}

