package view.ProfileMenu;

import controller.ProfileMenuController;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.OtherMenus.SignUpMenu;

import java.util.Objects;

public class ChangeNameMenu extends Application {
    public TextField newUsername;
    public Text name;
    public Circle circle;

    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(ChangeNameMenu.class.getResource("/view/ProfileMenu/ChangeNameMenu/ChangeNameMenu.fxml"));

        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        name.textProperty().set(ProfileMenuController.getCurrentUsername());
        circle.setFill(new ImagePattern(ProfileMenuController.getImage()));
    }


    public void change() throws Exception {
        String newName = newUsername.getText();

        if (Objects.equals(newName, ProfileMenuController.getCurrentUsername())) {
            prevUsername();
            return;
        }
        if (!SignUpMenuController.nameIsValid(newName)) {
            nameIsInvalid();
            return;
        }
        if (SignUpMenuController.usernameExists(newName)) {
            nameExists();
            return;
        }

        ProfileMenuController.changeCurrentUsername(newName);
        back();
    }

    private void nameIsInvalid() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Username is invalid.");
        alert.show();
    }

    private void nameExists() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Username \"%s\" already exists.".formatted(newUsername.getCharacters()));
        alert.show();
    }

    private void prevUsername() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Username is the same as the last one.");
        alert.show();
    }

    public void back() throws Exception {
        new ProfileMenu().start(SignUpMenu.stage);
    }
}
