package controller;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import view.GameMenu.GameMenu;

import java.net.URL;

public enum BackgroundSetter {
    GAME("1.jpg"),
    LOGIN("3.jpg"),
    MENU("2.jpg");

    private BackgroundImage backgroundImage;

    public BackgroundImage getBackgroundImage() {
        return backgroundImage;
    }

    BackgroundSetter(String name) {
        URL url = GameMenu.class.getResource("/Wallpapers/" + name);
        Image image = new Image(url.toExternalForm());
        backgroundImage = new BackgroundImage(image,
                                              BackgroundRepeat.NO_REPEAT,
                                              BackgroundRepeat.NO_REPEAT,
                                              BackgroundPosition.CENTER,
                                              BackgroundSize.DEFAULT);
    }
}
