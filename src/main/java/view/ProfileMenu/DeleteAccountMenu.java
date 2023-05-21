package view.ProfileMenu;

import controller.ProfileMenuController;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.OtherMenus.LoginMenu;
import view.OtherMenus.SignUpMenu;

import java.util.Objects;

public class DeleteAccountMenu extends Application {

    public TextField password;
    public TextField confirmation;
    public Text name;
    public Circle circle;


    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(DeleteAccountMenu.class.getResource("/view/ProfileMenu/DeleteAccountMenu/DeleteAccountMenu.fxml"));

        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        name.textProperty().set(ProfileMenuController.getCurrentUsername());
        circle.setFill(new ImagePattern(ProfileMenuController.getImage()));
    }

    public void delete() throws Exception {
        String passwordText = password.getText();
        String confirmationText = confirmation.getText();

        if (!Objects.equals(passwordText, confirmationText)) {
            ChangePassMenu.confirmationFailed();
            return;
        }
        if (!Objects.equals(passwordText, ProfileMenuController.getCurrentPassword())) {
            LoginMenu.passwordIsIncorrect();
            return;
        }

        deleteAccount();
    }

    private void deleteAccount() throws Exception {
        SignUpMenuController.deleteCurrentAccount();
        new SignUpMenu().start(SignUpMenu.stage);
    }

    public void back() throws Exception {
        new ProfileMenu().start(SignUpMenu.stage);
    }
}



