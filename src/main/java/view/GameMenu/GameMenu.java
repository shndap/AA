package view.GameMenu;

import controller.BackgroundSetter;
import controller.ControlKeys;
import controller.GameMenuController;
import controller.SettingMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.OtherMenus.SignUpMenu;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class GameMenu extends Application {

    private Media music;
    private MediaPlayer mediaPlayer;
    private Button pauseButton;
    private boolean isLoaded = false;

    private static void configureGroup(Group group, Pane pane, int v) {
        pane.getChildren().add(group);
        group.setLayoutX(250);
        group.setLayoutY(v);
    }

    private static void handlePauseButton(ActionEvent actionEvent) {
        try {
            new PauseMenu().start(SignUpMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Media getRandomSong() throws UnsupportedEncodingException, MalformedURLException {
        String base = "src/main/resources/Music";
        File file = new File(base);
        ArrayList<String> songs = new ArrayList<>();

        for (File song : file.listFiles())
            if (song.isFile())
                songs.add(song.getName());

        String song = songs.get((songs.size() + new Random().nextInt() % songs.size()) % songs.size());

        URL url = GameMenu.class.getResource("/Music/" + song);

        return new Media(url.toExternalForm());
    }

    public void setLoaded() {
        isLoaded = true;
    }

    private Pane setPane() {
        if (!isLoaded)
            GameMenuController.createNewGame();
        Pane pane = configurePane();
        GameMenuController.setStackPane(pane);
        return pane;
    }

    private Pane configurePane() {
        Pane pane = new Pane();
        pane.setMaxSize(500, 700);
        pane.setPrefSize(500, 700);

        configureGroup(GameMenuController.getShooter(), pane, 650);
        configureGroup(GameMenuController.centralCircle(), pane, 250);
        configurePauseButton(pane);

        configureDataBar(pane);

        return pane;
    }

    private void configurePauseButton(Pane pane) {
        pauseButton = new Button("||");
        pauseButton.setMaxSize(30, 30);
        pauseButton.setStyle("-fx-background-color: orange;" +
                                     " -fx-font: 15 System;" +
                                     " -fx-font-weight: bolder;" +
                                     " -fx-stroke-width: 2;" +
                                     " -fx-stroke: black;");
        pauseButton.setFocusTraversable(false);
        pauseButton.setOnAction(GameMenu::handlePauseButton);

        pane.getChildren().add(pauseButton);
    }

    private void configureDataBar(Pane pane) {
        HBox dataBar = GameMenuController.getDataBar();
        HBox freezingBarHbox = GameMenuController.getFreezingBarHbox();

        HBox hBox = new HBox(freezingBarHbox, pauseButton, dataBar);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(40);
        pane.getChildren().add(hBox);
        hBox.setMaxWidth(460);
        hBox.setLayoutX(20);
        hBox.setLayoutY(5);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (GameMenuController.getMediaPlayer() == null) {
            music = getRandomSong();
            mediaPlayer = new MediaPlayer(music);
            GameMenuController.setMediaPlayer(mediaPlayer);
        } else {
            mediaPlayer = GameMenuController.getMediaPlayer();
        }

        Pane pane = setPane();

        GameMenuController.startRotating();

        Scene scene = new Scene(pane);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeys);

        stage.setScene(scene);
        pane.setBackground(new Background(BackgroundSetter.GAME.getBackgroundImage()));
        mediaPlayer.play();
        stage.show();
    }

    private void freeze() {
        GameMenuController.freeze();
    }

    private void shoot() throws Exception {
        GameMenuController.shoot();

        if (GameMenuController.shouldStartPhase2()) GameMenuController.startPhase2();
        else if (GameMenuController.shouldStartPhase3()) GameMenuController.startPhase3();
        else if (GameMenuController.shouldStartPhase4()) GameMenuController.startPhase4();
        else if (GameMenuController.finished()) GameMenuController.won();
    }

    public void handleKeys(KeyEvent keyEvent) {
        if (GameMenuController.cannotPlay()) return;

        String key = keyEvent.getCode().toString();

        if (key.equalsIgnoreCase(SettingMenuController.getKey(ControlKeys.SHOOT1.getName()))) {
            try {
                shoot();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (key.equalsIgnoreCase(SettingMenuController.getKey(ControlKeys.FREEZE1.getName())) &&
                !GameMenuController.isFrozen() &&
                GameMenuController.barIsFull())
            freeze();
    }

}
