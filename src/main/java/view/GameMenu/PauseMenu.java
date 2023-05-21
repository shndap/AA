package view.GameMenu;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Saver.GameSaver;
import model.User.Users;
import view.OtherMenus.SignUpMenu;
import view.SettingMenu.ChooseKeysMenu;

public class PauseMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(500, 700);
        pane.setMaxSize(500, 700);

        Button button = new Button();
        button.setText("Back");
        button.setStyle("-fx-background-radius: 40; -fx-background-color: grey; -fx-font: 14 System;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                back();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setMaxSize(100, 30);
        button.setPrefSize(100, 30);

        pane.getChildren().add(button);


        GameMenuController.saveGame();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void saveGame() {
        // todo
    }

    private void restartGame() throws Exception {
        GameMenuController.createNewGame();
        new GameMenu().start(SignUpMenu.stage);
    }

    private void showControlKeys() throws Exception {
        ChooseKeysMenu chooseKeysMenu = new ChooseKeysMenu();
        chooseKeysMenu.ChooseKeysMenuInit(true);

        chooseKeysMenu.start(SignUpMenu.stage);
    }

    private void changeMusic() {

    }

    private void back() throws Exception {
        GameSaver savedGamed = GameSaver.getSavedGamed(Users.getCurrentUser().getUserName());
        savedGamed.load().start(SignUpMenu.stage);
    }
}
