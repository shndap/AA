package view.SettingMenu;

import controller.SettingMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.OtherMenus.MainMenu;
import view.OtherMenus.SignUpMenu;

public class SettingMenu extends Application {
    public Text levelName;
    public Text numberOfBallsName;
    public Slider levelBar;
    public Slider ballsBar;
    public Button muteButton;
    public Button bwButton;
    public Button languageButton;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(SettingMenu.class.getResource("/view/Setting/SettingMenu.fxml"));

        SettingMenuController.load(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        levelBar.valueProperty().addListener(observable -> {
            switch (levelBar.valueProperty().intValue()) {
                case 1 -> {
                    levelName.setText("Easy");
                }
                case 2 -> {
                    levelName.setText("Medium");
                }
                case 3 -> {
                    levelName.setText("Hard");
                }
            }
            SettingMenuController.setLevel(levelBar.valueProperty().intValue());
        });

        ballsBar.valueProperty().addListener(observable -> {
            numberOfBallsName.setText(ballsBar.valueProperty().intValue() + "");
            SettingMenuController.setBalls(ballsBar.valueProperty().intValue());
        });

        muteButton.setOnMouseClicked(event -> mute());
        bwButton.setOnMouseClicked(event -> bwMode());
        languageButton.setOnMouseClicked(event -> language());
    }

    public void chooseMap() throws Exception {
        new ChooseMapMenu().start(SignUpMenu.stage);
    }

    public void mute() {
        SettingMenuController.setMute(!SettingMenuController.isMute());
        muteButton.setText(SettingMenuController.isMute() ? "Unmute" : "Mute");
    }

    public void bwMode() {
        SettingMenuController.setBW(!SettingMenuController.isBW());
        bwButton.setText(SettingMenuController.isBW() ? "Set to Colorful Mode" : "Set to BW Mode");
    }

    public void language() {
        SettingMenuController.setLanguage(!SettingMenuController.isPersian());
        languageButton.setText(SettingMenuController.isPersian() ? "Change to English" : "Change to Persian");
    }

    public void changeControlKeys() throws Exception {
        ChooseKeysMenu chooseKeysMenu = new ChooseKeysMenu();
        chooseKeysMenu.ChooseKeysMenuInit(false);
        chooseKeysMenu.start(SignUpMenu.stage);
    }

    public void back() throws Exception {
        new MainMenu().start(SignUpMenu.stage);
    }
}
