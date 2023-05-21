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

        Animation startAnimation = getAnimation(Color.BLACK, Color.LIGHTBLUE);
        Animation endAnimation = getAnimation(Color.LIGHTBLUE, Color.BLACK);

        SequentialTransition sequentialTransition = new SequentialTransition(startAnimation, endAnimation);
        sequentialTransition.setOnFinished(
                actionEvent -> {
                    GameMenuController.getGame().getCentralCircle().getInnerCircle().setFill(Color.BLACK);
                    GameMenuController.continueRotation();
                });

        return sequentialTransition;
    }

    private static Animation getAnimation(Color from, Color to) {
        return new Transition() {
            private final double start = System.currentTimeMillis();

            {
                setCycleDuration(Duration.millis(GameMenuController.getFreezeDuration() / 2));
            }

            @Override
            protected void interpolate(double v) {
                Color newColor = getColor(from, to);
                Circle innerCircle = GameMenuController.getGame().getCentralCircle().getInnerCircle();
                innerCircle.setFill(newColor);
            }

            private Color getColor(Color from1, Color to1) {
                double r0 = from1.getRed() * 256, g0 = from1.getGreen() * 256, b0 = from1.getBlue() * 256;
                double rf = to1.getRed() * 256, gf = to1.getGreen() * 256, bf = to1.getBlue() * 256;

                double time = (System.currentTimeMillis() - start) % getCycleDuration().toMillis();

                double r = linearValue(r0, rf, time);
                double b = linearValue(b0, bf, time);
                double g = linearValue(g0, gf, time);

                return Color.rgb((int) r, (int) g, (int) b);
            }

            private double linearValue(double g0, double gf, double time) {
                return g0 + (gf - g0) / this.getCycleDuration().toMillis() * time;
            }
        };
    }

    public static Transition freezingBarAnimation() {
        return new Transition() {
            private final double duration = GameMenuController.getFreezeDuration();
            private final double start = System.currentTimeMillis();

            {
                setCycleDuration(Duration.millis(duration));
            }

            @Override
            protected void interpolate(double v) {
                double time = System.currentTimeMillis() - start;
                GameMenuController.setFreezingBar(1 - time / duration);
            }
        };
    }
}
