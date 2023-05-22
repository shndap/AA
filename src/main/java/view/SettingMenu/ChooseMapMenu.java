package view.SettingMenu;

import controller.BackgroundSetter;
import controller.SettingMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.OtherMenus.SignUpMenu;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChooseMapMenu extends Application {
    public static void main(String[] args) {
        launch();
    }

    private static ImageView getImageView(String path) {
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setMaxSize(500, 700);
        pane.setPrefSize(500, 700);
        HBox hBox = gethBox();
        HBox top = getTop();

        Button button = getBackButton();
        VBox vBox = getGreatvBox(hBox, top, button);
        pane.getChildren().add(vBox);


        Scene scene = new Scene(pane);
        pane.setBackground(new Background(BackgroundSetter.MENU.getBackgroundImage()));
        stage.setScene(scene);
        stage.show();
    }

    private Button getBackButton() {
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

        return button;
    }

    private VBox getGreatvBox(HBox hBox, HBox top, Button button) {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(top, hBox, button);
        vBox.setPrefSize(500, 700);
        vBox.setMaxSize(500, 700);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        return vBox;
    }

    private HBox gethBox() {
        VBox left = getvBox();
        VBox right = getvBox();
        HBox hBox = new HBox(left, right);
        hBox.setPrefSize(500, 500);
        addImages(left, right);
        return hBox;
    }

    private VBox getvBox() {
        VBox left = new VBox();
        left.setSpacing(15);
        left.setAlignment(Pos.CENTER);
        left.setPrefSize(250, 500);
        return left;
    }

    private HBox getTop() {
        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        Text text = new Text("Pick a map...");
        text.setStyle("-fx-font: 15 System;");
        top.getChildren().add(text);
        return top;
    }

    private void addImages(VBox left, VBox right) {
        String path_ = ChooseMapMenu.class.getResource("/view/Maps").getPath();
        File dir = new File(path_);

        File[] listFiles = dir.listFiles();
        for (int i = 0, listFilesLength = listFiles.length; i < listFilesLength; i++) {
            File file = listFiles[i];
            if (i < 3) left.getChildren().add(getImage(file.getPath()));
            else right.getChildren().add(getImage(file.getPath()));
        }
    }

    private VBox getImage(String path) {
        ImageView imageView = getImageView(path);

        String name = getName(path);
        Text text = new Text(name);

        if (Objects.equals(name, SettingMenuController.getMapName()))
            text.setStyle("-fx-font: 14 System; -fx-font-weight: bold;");
        else
            text.setStyle("-fx-font: 14 System;");

        VBox vBox = new VBox(imageView, text);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        imageView.setId(name);
        vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new WhatImage());

        return vBox;
    }

    private String getName(String path) {
        Pattern pattern = Pattern.compile(".*\\\\(?<name>.+).png\\\\?");
        Matcher matcher = pattern.matcher(path);
        matcher.find();
        return matcher.group("name");
    }

    private void back() throws Exception {
        new SettingMenu().start(SignUpMenu.stage);
    }

    private class WhatImage implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            String img = event.getPickResult().getIntersectedNode().getId();
            SettingMenuController.setMap(img);
            try {
                back();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
