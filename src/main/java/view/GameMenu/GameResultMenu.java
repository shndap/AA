package view.GameMenu;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Game.Game;
import view.OtherMenus.MainMenu;
import view.OtherMenus.SignUpMenu;

public class GameResultMenu extends Application {
    private Game game;

    private static VBox configureVBox() {
        VBox vBox = new VBox();
        vBox.setMaxSize(500, 700);
        vBox.setPrefSize(500, 700);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private static Button getButton() {
        Button button = new Button();
        button.setText("Back");
        button.setStyle("-fx-background-radius: 40; -fx-background-color: grey; -fx-font: 14 System;");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                new MainMenu().start(SignUpMenu.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        button.setMaxSize(100, 30);
        button.setPrefSize(100, 30);
        return button;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane(getVBox());
        pane.setMaxSize(500, 700);
        pane.setPrefSize(500, 700);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private VBox getVBox() {
        VBox vBox = configureVBox();
        Button button = getButton();

        vBox.getChildren().addAll(game.getResultVBox(), button);
        return vBox;
    }
}
