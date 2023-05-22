package view.OtherMenus;

import controller.BackgroundSetter;
import controller.ProfileMenuController;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SignUpMenu extends Application {
    public static Stage stage;
    public TextField password;
    public TextField username;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        SignUpMenu.stage = stage;
        Pane pane = FXMLLoader.load(SignUpMenu.class.getResource("/view/SignupMenu/SignUpMenu.fxml"));

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        pane.setBackground(new Background(BackgroundSetter.LOGIN.getBackgroundImage()));
        stage.show();
    }

    public void signup() throws Exception {
        String name = username.getCharacters().toString();
        String pass = password.getCharacters().toString();

        if (!SignUpMenuController.nameIsValid(name)) {
            nameIsInvalidError();
            return;
        }
        if (SignUpMenuController.usernameExists(name)) {
            nameExistsError();
            return;
        }
        if (!SignUpMenuController.passIsValid(pass)) {
            passIsInvalidError();
            return;
        }

        SignUpMenuController.addUser(name, pass, false);
        gotoProfileMenu(stage);
    }

    private void gotoProfileMenu(Stage stage) throws Exception {
        ProfileMenuController.setCurrentUser(username.getText());
        new MainMenu().start(stage);
    }

    private void passIsInvalidError() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Password must contain at least 8 non-white characters.");
        alert.show();
    }

    private void nameExistsError() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Username \"%s\" already exists.".formatted(username.getCharacters()));
        alert.show();
    }

    private void nameIsInvalidError() {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                                "Username must contain non-white characters.");
        alert.show();
    }

    public void guest() throws Exception {
        String name = SignUpMenuController.addGuestUser();
        username.textProperty().set(name);
        password.textProperty().set(name);
        gotoProfileMenu(stage);
    }

    public void login() throws Exception {
        new LoginMenu().start(SignUpMenu.stage);
    }
}
