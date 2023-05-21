package model.Game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Phase3 {
    private static Timeline timeline;

    public static void fadeOut(CentralCircle circle) {
        for (Node node : circle.getGroup().getChildren())
            if (node instanceof Line line) line.setStroke(Color.TRANSPARENT);
            else if (node instanceof Circle circle1)
                if (!circle1.equals(circle.getInnerCircle()) && !circle1.equals(circle.getOuterCircle()))
                    circle1.setFill(Color.TRANSPARENT);
    }

    public static void fadeIn(CentralCircle circle) {
        for (Node node : circle.getGroup().getChildren())
            if (node instanceof Line line) line.setStroke(Color.BLACK);
            else if (node instanceof Circle circle1)
                if (!circle1.equals(circle.getInnerCircle()) && !circle1.equals(circle.getOuterCircle()))
                    circle1.setFill(Color.BLACK);
    }

    public static Timeline getTimeline(CentralCircle circle) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            long diff = System.currentTimeMillis() - circle.getStartTimeInMilis();
            if (diff >= 0 && diff <= 1005) fadeOut(circle);
            else if (diff >= 1005) {
                fadeIn(circle);
                circle.setStartTimeInMilis(System.currentTimeMillis());
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }

    public static void stop() {
        if (timeline != null) timeline.stop();
    }

    public static void newPhase() {
        timeline = null;
    }
}
