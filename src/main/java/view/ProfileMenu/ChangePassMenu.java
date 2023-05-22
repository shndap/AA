package view.ProfileMenu;

import controller.BackgroundSetter;
import controller.ProfileMenuController;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.OtherMenus.LoginMenu;
import view.OtherMenus.SignUpMenu;

import java.util.Objects;

public class ChangePassMenu extends Application {
    public Text name;
    public TextField newPassword;
    public TextField confirmation;
    public TextField oldPassword;
    public Circle circle;

    public static void confirmationFailed() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "First and second passwords must match.");
        alert.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(ChangePassMenu.class.getResource("/view/ProfileMenu/ChangePassMenu/ChangePassMenu.fxml"));

        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        gamePane.setBackground(new Background(BackgroundSetter.MENU.getBackgroundImage()));
        stage.show();
    }

    @FXML
    public void initialize() {
        name.textProperty().set(ProfileMenuController.getCurrentUsername());
        circle.setFill(new ImagePattern(ProfileMenuController.getImage()));
    }

    public void change() throws Exception {
        String newPass = newPassword.getText();
        String confirm = confirmation.getText();
        String oldPass = oldPassword.getText();

        if (!Objects.equals(oldPass, ProfileMenuController.getCurrentPassword())) {
            LoginMenu.passwordIsIncorrect();
            return;
        }
        if (!Objects.equals(newPass, confirm)) {
            confirmationFailed();
            return;
        }
        if (Objects.equals(newPass, ProfileMenuController.getCurrentPassword())) {
            prevPass();
            return;
        }
        if (!SignUpMenuController.passIsValid(newPass)) {
            passIsInvalid();
            return;
        }

        ProfileMenuController.changeCurrentPass(newPass);
        back();
    }

    private void passIsInvalid() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Password should be at least 8 characters.");
        alert.show();
    }

    private void prevPass() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Password is the same as the last one.");
        alert.show();
    }

    public void back() throws Exception {
        new ProfileMenu().start(SignUpMenu.stage);
    }
}
