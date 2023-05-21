package view.GameMenu;

import controller.GameMenuController;
import controller.MusicMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import view.OtherMenus.SignUpMenu;

public class ChooseMusicMenu extends Application {
    private Text songName;
    private Button prevButton;
    private Button nextButton;
    private Button backButton;
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setMaxSize(500, 700);
        pane.setPrefSize(500, 700);

        VBox vBox = getvBox();

        pane.getChildren().add(vBox);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private VBox getvBox() {
        getNextButton();
        getText();
        getPrevButton();
        getBackButton();
        HBox hBox = new HBox(prevButton, songName, nextButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        VBox vBox = new VBox(hBox);
        vBox.setMaxSize(500, 600);
        vBox.setPrefSize(500, 600);
        vBox.setAlignment(Pos.CENTER);

        VBox vBox1 = new VBox(vBox, backButton);
        vBox1.setAlignment(Pos.CENTER);
        return vBox1;
    }

    private void getBackButton() {
        backButton = new Button();
        backButton.setText("Back");
        backButton.setStyle("-fx-background-radius: 40; -fx-background-color: grey; -fx-font: 14 System;");
        backButton.setOnMouseClicked(this::handleBack);
        backButton.setMaxSize(100, 30);
        backButton.setPrefSize(100, 30);
    }

    private void back() throws Exception {
        new PauseMenu().start(SignUpMenu.stage);
    }

    private void getText() {
        songName = new Text(MusicMenuController.getCurrentSongName().replaceAll("_", " "));
        songName.setStyle("-fx-fill: purple; -fx-font: 20 System; -fx-background-radius: 40");
    }

    private void getPrevButton() {
        prevButton = new Button("◀");
        prevButton.setStyle("-fx-background-radius: 40; -fx-background-color: Blue; -fx-font: 14 System;");
        prevButton.setOnAction(this::setPreviousSong);
    }


    private void getNextButton() {
        nextButton = new Button("▶");
        nextButton.setStyle("-fx-background-radius: 40; -fx-background-color: Blue; -fx-font: 14 System;");
        nextButton.setOnAction(this::setNextSong);
    }


    private void setPreviousSong(ActionEvent actionEvent) {
        Media previousSong = MusicMenuController.getPreviousSong();
        GameMenuController.setMediaPlayer(new MediaPlayer(previousSong));
        listener();
    }

    private void setNextSong(ActionEvent actionEvent) {
        Media NextSong = MusicMenuController.getNextSong();
        GameMenuController.setMediaPlayer(new MediaPlayer(NextSong));
        listener();
    }

    private void listener() {
        songName.setText(MusicMenuController.getCurrentSongName().replaceAll("_", " "));
    }

    private void handleBack(MouseEvent mouseEvent) {
        try {
            back();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
