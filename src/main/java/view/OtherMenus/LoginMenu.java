package view.OtherMenus;

import controller.BackgroundSetter;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginMenu extends Application {
    public TextField password;
    public TextField username;

    public static void passwordIsIncorrect() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Password is incorrect.");
        alert.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(LoginMenu.class.getResource("/view/SignupMenu/LoginMenu.fxml"));

        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        gamePane.setBackground(new Background(BackgroundSetter.LOGIN.getBackgroundImage()));
        stage.show();
    }

    public void login() throws Exception {
        String name = username.getText();
        String pass = password.getText();

        if (!SignUpMenuController.usernameExists(name)) {
            usernameNotExists();
            return;
        }
        if (!SignUpMenuController.passMatches(name, pass)) {
            passwordIsIncorrect();
            return;
        }

        MainMenu mainMenu = new MainMenu();
        mainMenu.setUser(name);
        mainMenu.start(SignUpMenu.stage);
    }

    private void usernameNotExists() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Username does not exist.");
        alert.show();
    }

    public void signUpMenu() throws Exception {
        new SignUpMenu().start(SignUpMenu.stage);
    }
}
