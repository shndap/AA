package model.Game;

import controller.GameMenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Phase2 {
    private static ScaleTransition scaleTransition;
    private static Timeline timeline;

    public static Timeline getRotationTimeLine() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(5),
                             event -> GameMenuController.reverseRotation()));

        timeline.setCycleCount(Animation.INDEFINITE);

        return timeline;
    }

    public static void addPulsation(CentralCircle circle, Circle ball) {
        scaleTransition = new ScaleTransition(Duration.seconds(1), ball);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.play();
        ball.radiusProperty().addListener(observable -> {
            if (circle.intersects(ball)) {
                try {
                    GameMenuController.lost();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void stop() {
        if (timeline != null) timeline.stop();
        if (scaleTransition != null) scaleTransition.stop();
    }

    public static void newPhase() {
        timeline = null;
        scaleTransition = null;
    }
}
