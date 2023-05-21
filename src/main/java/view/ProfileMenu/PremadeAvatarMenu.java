package view.ProfileMenu;

import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.OtherMenus.SignUpMenu;

import java.net.URL;

public class PremadeAvatarMenu extends Application {
    public HBox[] HB;
    public HBox H1;
    public HBox H2;
    public HBox H3;
    public HBox H4;

    public PremadeAvatarMenu() {
        addHBoxes();
        addImages();
    }

    private void addImages() {
        for (int i = 1; i <= 16; i++) addImage(i);
    }

    private void addHBoxes() {
        H1 = new HBox();
        H2 = new HBox();
        H3 = new HBox();
        H4 = new HBox();
        HB = new HBox[]{H1, H2, H3, H4};

        for (HBox hBox : HB) {
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(5);
        }
    }

    private void addImage(int i) {
        URL url = PremadeAvatarMenu.class.getResource("/view/ProfileMenu/ChangeAvatarMenu/Avatars/256_" + i + ".png");

        Image image = new Image(url.toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        imageView.setId("256_" + i + ".png");
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new WhatImage());
        HB[(i - 1) / 4].getChildren().add(imageView);
    }

    @Override
    public void start(Stage stage) throws Exception {
        SignUpMenu.stage = stage;

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        Text text = new Text("Choose an avatar...");
        text.setStyle("-fx-font: 16 System;");
        vBox.getChildren().addAll(text, H1, H2, H3, H4);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 500, 700, Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }

    private class WhatImage implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            String img = event.getPickResult().getIntersectedNode().getId();
            String imgPath = PremadeAvatarMenu.class.getResource(
                    "/view/ProfileMenu/ChangeAvatarMenu/Avatars/" + img).toExternalForm();
            SignUpMenuController.setAvatar(imgPath);
            try {
                new ProfileMenu().start(SignUpMenu.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
