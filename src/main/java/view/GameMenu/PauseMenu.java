package view.GameMenu;

import controller.BackgroundSetter;
import controller.GameMenuController;
import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.OtherMenus.MainMenu;
import view.OtherMenus.SignUpMenu;

import java.io.IOException;

public class PauseMenu extends Application {
    private static void showSaveAlert(Alert.AlertType information, String s) {
        new Alert(information, s).show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(500, 700);
        pane.setMaxSize(500, 700);


        pane.getChildren().add(getVBox());

        GameMenuController.saveGame();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        pane.setBackground(new Background(BackgroundSetter.GAME.getBackgroundImage()));
        stage.show();
    }

    private Button getBackButton() {
        Button button = new Button();
        button.setText("Back");
        button.setStyle("-fx-background-radius: 41; -fx-background-color: grey; -fx-font: 20 System;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                back();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setMaxSize(200, 50);
        button.setPrefSize(200, 50);
        return button;
    }

    private Button getSaveButton() {
        Button button = new Button();
        button.setText("Save");
        button.setStyle("-fx-background-radius: 40; -fx-background-color: magenta; -fx-font: 20 System;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                saveGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setMaxSize(200, 50);
        button.setPrefSize(200, 50);
        return button;
    }

    private Button getHelpButton() {
        Button button = new Button();
        button.setText("Show Control Keys");
        button.setStyle("-fx-background-radius: 40; -fx-background-color: purple; -fx-font: 20 System;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                showControlKeys();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setMaxSize(200, 50);
        button.setPrefSize(200, 50);
        return button;
    }

    private Button getRestartButton() {
        Button button = new Button();
        button.setText("Restart");
        button.setStyle("-fx-background-radius: 40; -fx-background-color: skyblue; -fx-font: 20 System;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                restartGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setMaxSize(200, 50);
        button.setPrefSize(200, 50);
        return button;
    }

    private Button getChangeMusicButton() {
        Button button = new Button();
        button.setText("Change Music");
        button.setStyle("-fx-background-radius: 40; -fx-background-color: lime; -fx-font: 20 System;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                changeMusic();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setMaxSize(200, 50);
        button.setPrefSize(200, 50);
        return button;
    }

    private Button getQuitButton() {
        Button button = new Button();
        button.setText("Quit");
        button.setStyle("-fx-background-radius: 40; -fx-background-color: red; -fx-font: 20 System;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                quit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setMaxSize(200, 50);
        button.setPrefSize(200, 50);
        return button;
    }

    private VBox getVBox() {
        VBox vBox = new VBox(getSaveButton(),
                             getRestartButton(),
                             getHelpButton(),
                             getChangeMusicButton(),
                             getQuitButton(),
                             getBackButton());

        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(500, 700);
        vBox.setMaxSize(500, 700);
        vBox.setSpacing(20);

        return vBox;
    }

    private void quit() throws Exception {
        GameMenuController.noMedia();
        GameMenuController.stopActivities();
        new MainMenu().start(SignUpMenu.stage);
    }

    private void saveGame() throws IOException {
        if (ProfileMenuController.isGuest()) {
            showSaveAlert(Alert.AlertType.ERROR, "You cannot save a game as a guest.");
            return;
        }

        showSaveAlert(Alert.AlertType.INFORMATION, "Game saved.");
        GameMenuController.saveGame();

    }

    private void restartGame() throws Exception {
        GameMenuController.noMedia();
        GameMenuController.createNewGame();
        new GameMenu().start(SignUpMenu.stage);
    }

    private void showControlKeys() {
        ProfileMenuController.getControlKeysAlert().show();
    }

    private void changeMusic() throws Exception {
        new ChooseMusicMenu().start(SignUpMenu.stage);
    }

    private void back() throws Exception {
        GameMenuController.loadGame();
    }
}
