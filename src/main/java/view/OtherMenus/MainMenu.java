package view.OtherMenus;

import controller.GameMenuController;
import controller.ProfileMenuController;
import controller.StarterController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.GameMenu.GameMenu;
import view.ProfileMenu.ProfileMenu;
import view.SettingMenu.ScoreboardMenu;
import view.SettingMenu.SettingMenu;

public class MainMenu extends Application {

    public Circle circle;
    public Text name;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(MainMenu.class.getResource("/view/MainMenu/MainMenu.fxml"));

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        name.textProperty().set(ProfileMenuController.getName());
        circle.setFill(new ImagePattern(ProfileMenuController.getImage()));
    }

    public void newGame() throws Exception {
        new GameMenu().start(SignUpMenu.stage);
    }

    public void loadGame() throws Exception {
        if (!ProfileMenuController.hasSavedGame()) {
            new Alert(Alert.AlertType.ERROR, "You don't have a saved game!").show();
            return;
        }

        GameMenuController.loadGame();
    }

    public void editProfile() throws Exception {
        new ProfileMenu().start(SignUpMenu.stage);
    }

    public void showScoreboard() throws Exception {
        ScoreboardMenu scoreboardMenu = new ScoreboardMenu();
        scoreboardMenu.setLevel(0);
        scoreboardMenu.start(SignUpMenu.stage);
    }

    public void setting() throws Exception {
        new SettingMenu().start(SignUpMenu.stage);
    }

    public void exit() {
        StarterController.saveUsers();
        System.exit(0);
    }

    public void setUser(String name) {
        ProfileMenuController.setCurrentUser(name);
    }
}
