package model.Game;

import controller.GameMenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.User.Preferance;

public class Phase4 {
    private static Timeline timeline;
    private static Scene scene;
    private static EventHandler<KeyEvent> keyEventEventHandler;

    public static void newPhase() {
        timeline = null;
        scene = null;
        keyEventEventHandler = null;
    }

    private static void addMovement(Scene scene, Shooter shooter, Preferance preferance) {
        Phase4.scene = scene;
        keyEventEventHandler = keyEvent -> {
            String key = keyEvent.getCode().toString().toLowerCase();

            if (key.equals(preferance.keys.get("left1"))) shooter.moveLeft();
            else if (key.equals(preferance.keys.get("right1"))) shooter.moveRight();
        };
        scene.setOnKeyPressed(keyEventEventHandler);
    }

    private static void addCannonRotation(Shooter shooter, Preferance preferance) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), actionEvent -> {
            double previousAngle = shooter.getCannonAngle();
            double newAngle = GameMenuController.getToLeft() ?
                    previousAngle + GameMenuController.getWindSpeed() :
                    previousAngle - GameMenuController.getWindSpeed();

            if (newAngle <= -15) {
                newAngle = -15;
                GameMenuController.setToLeft(true);
            } else if (newAngle >= 15) {
                newAngle = 15;
                GameMenuController.setToLeft(false);
            }

            shooter.setCannonAngle(newAngle);
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void startPhase4(Scene scene, Shooter shooter, Preferance preferance) {
        if (shooter.hasRotation()) return;
        else shooter.addRotation();
        addMovement(scene, shooter, preferance);
        addCannonRotation(shooter, preferance);
    }

    public static void stop() {
        if (timeline != null) timeline.stop();
        if (keyEventEventHandler != null) scene.removeEventHandler(KeyEvent.ANY, keyEventEventHandler);
    }
}
