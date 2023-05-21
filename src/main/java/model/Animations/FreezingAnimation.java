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
                duration - GameMenuController.getFreezeDuration() / 2,
                elapsed
        );
        Animation endAnimation = getAnimation();

        SequentialTransition sequentialTransition = new SequentialTransition(startAnimation, endAnimation);
        sequentialTransition.setOnFinished(
                actionEvent -> {
                    GameMenuController.getGame().getCentralCircle().getInnerCircle().setFill(Color.BLACK);
                    GameMenuController.continueRotation();
                    System.out.println("finished");
                });

        return sequentialTransition;
    }

    private static Animation getHalfAnimation(double duration) {
        double v = GameMenuController.getFreezeDuration() / 2;
        Animation endAnimation = getAnimation(getColor(Color.LIGHTBLUE, Color.BLACK, v - duration, v),
                                              Color.BLACK,
                                              duration,
                                              v - duration);

        endAnimation.setOnFinished(
                actionEvent -> {
                    GameMenuController.getGame().getCentralCircle().getInnerCircle().setFill(Color.BLACK);
                    GameMenuController.continueRotation();
                });

        return endAnimation;
    }

    private static Animation getAnimation(Color from, Color to, double duration, double elapsed) {
        return new Transition() {
            private final double start = System.currentTimeMillis() - elapsed;
            private final double cycle = GameMenuController.getFreezeDuration() / 2;

            {
                setCycleDuration(Duration.millis(duration));
            }

            @Override
            protected void interpolate(double v) {
                if (Math.abs(GameMenuController.getRotation().getRate()) > 0.2) {
                    GameMenuController.getRotation().setRate(GameMenuController.isInverse() ? 0.2 : -0.2);
                }

                Color newColor = getColor(from, to,
                                          (System.currentTimeMillis() - start) % cycle,
                                          cycle);
                Circle innerCircle = GameMenuController.getGame().getCentralCircle().getInnerCircle();
                innerCircle.setFill(newColor);
            }
        };
    }

    private static Animation getAnimation() {
        return getAnimation(Color.LIGHTBLUE,
                            Color.BLACK,
                            GameMenuController.getFreezeDuration() / 2,
                            0);
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
        double r0 = from1.getRed() * 255, g0 = from1.getGreen() * 255, b0 = from1.getBlue() * 255;
        double rf = to1.getRed() * 255, gf = to1.getGreen() * 255, bf = to1.getBlue() * 255;

        double r = linearValue(r0, rf, time, cycle);
        double b = linearValue(b0, bf, time, cycle);
        double g = linearValue(g0, gf, time, cycle);

        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static double linearValue(double g0, double gf, double time, double duration) {
        return g0 + (gf - g0) / duration * time;
    }
}
