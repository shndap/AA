package model.Animations;

import controller.GameMenuController;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class FreezingAnimation {
    public static Animation getFreezingAnimation() {
        return getFreezingAnimation(GameMenuController.getFreezeDuration());
    }

    public static Animation getFreezingAnimation(double duration) {
        double elapsed = GameMenuController.getFreezeDuration() - duration;

        if (elapsed >= GameMenuController.getFreezeDuration() / 2) {
            return getHalfAnimation(duration);
        }

        Animation startAnimation = getAnimation(
                getColor(Color.BLACK, Color.LIGHTBLUE, elapsed, GameMenuController.getFreezeDuration() / 2),
                Color.LIGHTBLUE,
                duration - GameMenuController.getFreezeDuration() / 2
        );
        Animation endAnimation = getAnimation();

        SequentialTransition sequentialTransition = new SequentialTransition(startAnimation, endAnimation);
        sequentialTransition.setOnFinished(
                actionEvent -> {
                    GameMenuController.getGame().getCentralCircle().getInnerCircle().setFill(Color.BLACK);
                    GameMenuController.continueRotation();
                });

        return sequentialTransition;
    }

    private static Animation getHalfAnimation(double duration) {
        Animation endAnimation = getAnimation(Color.LIGHTBLUE, Color.BLACK, duration);

        endAnimation.setOnFinished(
                actionEvent -> {
                    GameMenuController.getGame().getCentralCircle().getInnerCircle().setFill(Color.BLACK);
                    GameMenuController.continueRotation();
                });

        return endAnimation;
    }

    private static Animation getAnimation(Color from, Color to, double duration) {
        return new Transition() {
            private final double start = System.currentTimeMillis();

            {
                setCycleDuration(Duration.millis(duration));
            }

            @Override
            protected void interpolate(double v) {
                Color newColor = getColor(from, to,
                                          (System.currentTimeMillis() - start) % getCycleDuration().toMillis(),
                                          getCycleDuration().toMillis());
                Circle innerCircle = GameMenuController.getGame().getCentralCircle().getInnerCircle();
                innerCircle.setFill(newColor);
            }
        };
    }

    private static Animation getAnimation() {
        return getAnimation(Color.LIGHTBLUE, Color.BLACK, GameMenuController.getFreezeDuration() / 2);
    }

    public static Transition freezingBarAnimation() {
        return freezingBarAnimation(GameMenuController.getFreezeDuration());
    }

    public static Transition freezingBarAnimation(double duration) {
        return new Transition() {
            private final double freezeDuration = GameMenuController.getFreezeDuration();
            private final double start = System.currentTimeMillis() + duration - freezeDuration;

            {
                setCycleDuration(Duration.millis(duration));
            }

            @Override
            protected void interpolate(double v) {
                double time = System.currentTimeMillis() - start;
                GameMenuController.setFreezingBar(1 - time / freezeDuration);
            }
        };
    }

    public static Color getColor(Color from1, Color to1, double time, double cycle) {
        double r0 = from1.getRed() * 256, g0 = from1.getGreen() * 256, b0 = from1.getBlue() * 256;
        double rf = to1.getRed() * 256, gf = to1.getGreen() * 256, bf = to1.getBlue() * 256;

        double r = linearValue(r0, rf, time, cycle);
        double b = linearValue(b0, bf, time, cycle);
        double g = linearValue(g0, gf, time, cycle);

        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static double linearValue(double g0, double gf, double time, double duration) {
        return g0 + (gf - g0) / duration * time;
    }
}
