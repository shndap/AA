package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Maps;
import model.User.*;

public class SettingMenuController {

    private static User getUser() {
        return Users.getCurrentUser();
    }

    public static boolean isMute() {
        return getUser().getMute() == SoundMode.MUTE;
    }

    public static void setMute(boolean mute) {
        getUser().setMute(mute ? SoundMode.MUTE : SoundMode.UNMUTE);
    }

    public static boolean isBW() {
        return getUser().getBW() == ColorMode.BW;
    }

    public static void setBW(boolean bw) {
        getUser().setBW(bw ? ColorMode.BW : ColorMode.COLORFUL);
    }

    public static void setLanguage(boolean isPersian) {
        getUser().setLanguage(isPersian ? Language.PERSIAN : Language.ENGLISH);
    }

    public static boolean isPersian() {
        return getUser().getLanguage() == Language.PERSIAN;
    }

    public static void load(Pane pane) {
        VBox vBox = (VBox) pane.getChildren().get(0);
        VBox vBox1 = (VBox) vBox.getChildren().get(0);

        loadLevelBar(vBox1);
        loadBallsBar(vBox1);
        loadButtons(vBox1);
    }

    private static void loadButtons(VBox vBox1) {
        Button mute = (Button) vBox1.getChildren().get(3);
        mute.setText(isMute() ? "Unmute" : "Mute");
        Button bwMode = (Button) vBox1.getChildren().get(4);
        bwMode.setText(isBW() ? "Set to Colorful Mode" : "Set to BW Mode");
        Button language = (Button) vBox1.getChildren().get(5);
        language.setText(isPersian() ? "Change to english" : "Change to persian");
    }

    private static void loadBallsBar(VBox vBox1) {
        HBox ballsHBox = (HBox) vBox1.getChildren().get(1);
        Text ballsText = (Text) ballsHBox.getChildren().get(2);
        ballsText.setText("" + getUser().getNumberOfBalls());
        Slider ballsSlider = (Slider) ballsHBox.getChildren().get(1);
        ballsSlider.valueProperty().set(getUser().getNumberOfBalls());
    }

    private static void loadLevelBar(VBox vBox1) {
        HBox levelHBox = (HBox) vBox1.getChildren().get(0);
        Text levelText = (Text) levelHBox.getChildren().get(2);
        switch (getUser().getLevel()) {
            case 1 -> {
                levelText.setText("Easy");
            }
            case 2 -> {
                levelText.setText("Medium");
            }
            case 3 -> {
                levelText.setText("Hard");
            }
        }
        Slider levelSlider = (Slider) levelHBox.getChildren().get(1);
        levelSlider.valueProperty().set(getUser().getLevel());
    }

    public static void setLevel(int level) {
        getUser().setLevel(level);
    }

    public static void setBalls(int balls) {
        getUser().setNumberOfBalls(balls);
    }

    public static void setMap(String img) {
        for (Maps maps : Maps.values()) {
            if (maps.name().equalsIgnoreCase(img)) {
                getUser().setMap(maps);
                return;
            }
        }
    }

    public static String getMapName() {
        return getUser().getMap().name().toLowerCase();
    }

    public static String getKey(String key) {
        return getUser().getKey(key);
    }

    public static void setKey(String buttonName, String key) {
        getUser().setKey(buttonName, key);
    }
}
