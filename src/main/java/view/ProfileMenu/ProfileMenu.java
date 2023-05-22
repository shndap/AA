package view.ProfileMenu;

import controller.BackgroundSetter;
import controller.ProfileMenuController;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.OtherMenus.MainMenu;
import view.OtherMenus.SignUpMenu;

import java.net.URL;
import java.util.Objects;

public class ProfileMenu extends Application {
    private final ProfileMenuController profileMenuController;
    public Text name;
    public Circle circle;

    public ProfileMenu() {
        profileMenuController = new ProfileMenuController();
        name = new Text(ProfileMenuController.getName());
    }

    @FXML
    public void initialize() {
        name.textProperty().set(ProfileMenuController.getName());
        circle.setFill(new ImagePattern(ProfileMenuController.getImage()));
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new URL(ProfileMenu.class.getResource("/view/ProfileMenu/ProfileMenu.fxml").toString());
        Pane gamePane = FXMLLoader.load(Objects.requireNonNull(url));

        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        gamePane.setBackground(new Background(BackgroundSetter.MENU.getBackgroundImage()));
        stage.show();
    }

    public void changeName() throws Exception {
        new ChangeNameMenu().start(SignUpMenu.stage);
    }

    public void changePass() throws Exception {
        new ChangePassMenu().start(SignUpMenu.stage);
    }

    public void changeAvatar() throws Exception {
        new ChangeAvatarMenu().start(SignUpMenu.stage);
    }

    public void logout() throws Exception {
        SignUpMenuController.logoutCurrentUser();
        new SignUpMenu().start(SignUpMenu.stage);
    }

    public void deleteAccount() throws Exception {
        SignUpMenuController.logoutCurrentUser();
        new DeleteAccountMenu().start(SignUpMenu.stage);
    }

    public void back() throws Exception {
        new MainMenu().start(SignUpMenu.stage);
    }
}
