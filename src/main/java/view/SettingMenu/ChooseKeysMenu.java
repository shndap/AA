package view.SettingMenu;

import controller.SettingMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.GameMenu.PauseMenu;
import view.OtherMenus.SignUpMenu;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

public class ChooseKeysMenu extends Application {
    public HashMap<String, Button> buttons;
    public HashMap<String, Boolean> buttonActive;
    private Pane pane;
    @FXML
    private Button shoot1;
    @FXML
    private Button freeze1;
    @FXML
    private Button left1;
    @FXML
    private Button right1;
    @FXML
    private Button shoot2;
    @FXML
    private Button freeze2;
    @FXML
    private Button left2;
    @FXML
    private Button right2;
    @FXML
    private Scene scene;
    private boolean isFromPause;

    public void ChooseKeysMenuInit(boolean isFromPause) throws IOException {
        load();
        scene = new Scene(pane);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
        initButtons();
        this.isFromPause = isFromPause;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(scene);
        stage.show();
    }

    private void load() throws IOException {
        FXMLLoader loader = new FXMLLoader(new URL(
                ChooseKeysMenu.class.getResource("/view/Setting/ChooseKeys.fxml").toExternalForm()
        ));

        pane = loader.load();

        loadButtons(loader);
        addActions();
    }

    private void addActions() {
        shoot1.setOnAction(this::shoot1pressed);
        freeze1.setOnAction(this::freeze1pressed);
        left1.setOnAction(this::left1pressed);
        right1.setOnAction(this::right1pressed);

        shoot2.setOnAction(this::shoot2pressed);
        freeze2.setOnAction(this::freeze2pressed);
        left2.setOnAction(this::left2pressed);
        right2.setOnAction(this::right2pressed);
    }

    private void loadButtons(FXMLLoader loader) {
        shoot1 = (Button) loader.getNamespace().get("shoot1");
        freeze1 = (Button) loader.getNamespace().get("freeze1");
        left1 = (Button) loader.getNamespace().get("left1");
        right1 = (Button) loader.getNamespace().get("right1");
        shoot2 = (Button) loader.getNamespace().get("shoot2");
        freeze2 = (Button) loader.getNamespace().get("freeze2");
        left2 = (Button) loader.getNamespace().get("left2");
        right2 = (Button) loader.getNamespace().get("right2");

        unfocused();
    }

    private void unfocused() {
        shoot1.setFocusTraversable(false);
        freeze1.setFocusTraversable(false);
        left1.setFocusTraversable(false);
        right1.setFocusTraversable(false);
        shoot2.setFocusTraversable(false);
        freeze2.setFocusTraversable(false);
        left2.setFocusTraversable(false);
        right2.setFocusTraversable(false);
    }

    private void initButtons() {
        configureButtons();
        configureButtonActive();

        for (Entry<String, Button> button : buttons.entrySet())
            button.getValue().setText(SettingMenuController.getKey(button.getKey()));
    }

    private void configureButtons() {
        buttons = new HashMap<>();
        buttons.put("shoot1", shoot1);
        buttons.put("shoot2", shoot2);
        buttons.put("freeze1", freeze1);
        buttons.put("freeze2", freeze2);
        buttons.put("left1", left1);
        buttons.put("left2", left2);
        buttons.put("right1", right1);
        buttons.put("right2", right2);
    }

    private void configureButtonActive() {
        buttonActive = new HashMap<>();
        buttonActive.put("shoot1", false);
        buttonActive.put("shoot2", false);
        buttonActive.put("freeze1", false);
        buttonActive.put("freeze2", false);
        buttonActive.put("left1", false);
        buttonActive.put("left2", false);
        buttonActive.put("right1", false);
        buttonActive.put("right2", false);
    }

    private void setToBold(Button button) {
        button.setStyle("-fx-font: 13 System; -fx-font-weight: bold;");
    }

    private void setToNormal(Button button) {
        button.setStyle("-fx-font: 13 System;");
    }

    public void shoot1pressed(ActionEvent actionEvent) {
        setToBold(buttons.get("shoot1"));
        MakeActive("shoot1");
    }

    private boolean hasKeyForOthers(String key) {
        for (Entry<String, Button> buttonEntry : buttons.entrySet())
            if (SettingMenuController.getKey(buttonEntry.getKey()).equals(key))
                return true;
        return false;
    }

    private void MakeActive(String button) {
        buttonActive = new HashMap<>();
        for (String buttonName : buttons.keySet()) {
            buttonActive.put(buttonName, buttonName.equals(button));
            if (buttonName.equals(button)) setToBold(buttons.get(buttonName));
            else setToNormal(buttons.get(buttonName));
        }
    }

    public void freeze1pressed(ActionEvent actionEvent) {
        setToBold(buttons.get("freeze1"));
        MakeActive("freeze1");
    }

    public void left1pressed(ActionEvent actionEvent) {
        setToBold(buttons.get("left1"));
        MakeActive("left1");
    }

    public void right1pressed(ActionEvent actionEvent) {
        setToBold(buttons.get("right1"));
        MakeActive("right1");
    }

    public void shoot2pressed(ActionEvent actionEvent) {
        setToBold(buttons.get("shoot2"));
        MakeActive("shoot2");
    }

    public void freeze2pressed(ActionEvent actionEvent) {
        setToBold(buttons.get("freeze2"));
        MakeActive("freeze2");
    }

    public void left2pressed(ActionEvent actionEvent) {
        setToBold(buttons.get("left2"));
        MakeActive("left2");
    }

    public void right2pressed(ActionEvent actionEvent) {
        setToBold(buttons.get("right2"));
        MakeActive("right2");
    }

    public void back() throws Exception {
        if (!isFromPause) new SettingMenu().start(SignUpMenu.stage);
        else new PauseMenu().start(SignUpMenu.stage);
    }

    private void handleKeyPressed(KeyEvent keyEvent) {
        String key = keyEvent.getCode().toString().toLowerCase();
        String buttonName = "";

        for (Entry<String, Boolean> entry : buttonActive.entrySet()) {
            if (entry.getValue()) {
                buttonName = entry.getKey();

                if (!hasKeyForOthers(key)) break;
                else buttonName = "";
            }
        }

        if (!buttonName.equals("")) {
            Button button = buttons.get(buttonName);
            button.setText(key);
            SettingMenuController.setKey(buttonName, key);
            setToNormal(button);
            configureButtonActive();
        }
    }
}
