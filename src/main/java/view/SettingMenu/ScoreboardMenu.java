package view.SettingMenu;

import controller.ScoreboardController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.OtherMenus.MainMenu;
import view.OtherMenus.SignUpMenu;

public class ScoreboardMenu extends Application {
    private final ScoreboardController scoreboardController;
    public VBox table;
    private int level;

    public ScoreboardMenu() {
        this.scoreboardController = new ScoreboardController();
    }

    public static void main(String[] args) {
        launch();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level - 1;
    }

    @Override
    public void start(Stage stage) throws Exception {
        scoreboardController.initPane(level);
        Scene scene = new Scene(scoreboardController.getPane());
        stage.setScene(scene);
        stage.show();
    }

    public void level1() throws Exception {
        setLevel(1);
        start(SignUpMenu.stage);
    }

    public void level2() throws Exception {
        setLevel(2);
        start(SignUpMenu.stage);
    }

    public void level3() throws Exception {
        setLevel(3);
        start(SignUpMenu.stage);
    }

    public void overall() throws Exception {
        setLevel(0);
        start(SignUpMenu.stage);
    }

    public void back() throws Exception {
        new MainMenu().start(SignUpMenu.stage);
    }
}
