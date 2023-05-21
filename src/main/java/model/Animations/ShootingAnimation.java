package model.Animations;

import controller.GameMenuController;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Game.CentralCircle;
import model.Game.Shooter;

public class ShootingAnimation extends Transition {

    private final Pane pane;
    private final Shooter shooter;
    private final Circle ball;
    private final CentralCircle centralCircle;
    private final double angle;


    public ShootingAnimation(Pane pane, Shooter shooter, Circle ball, CentralCircle centralCircle) {
        this.pane = pane;
        this.shooter = shooter;
        this.ball = ball;
        this.centralCircle = centralCircle;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        angle = shooter.getCannonAngle();
    }

    @Override
    protected void interpolate(double v) {
        double speed = 10;
        double y = ball.getCenterY() - speed * Math.cos(Math.toRadians(angle));
        double x = ball.getCenterX() + speed * Math.sin(Math.toRadians(angle));

        if (centralCircle.intersects(ball) || (x <= 20 || x >= 480)) {
            try {
                this.stop();
                GameMenuController.lost();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            pane.getChildren().remove(ball);
            this.stop();
        }

        if (y <= 50) {
            pane.getChildren().remove(ball);
            this.stop();
        }

        if (centralCircle.canBePlaced(ball)) {
            GameMenuController.addBall(ball);
            GameMenuController.getGame().getDataBar().addScore();
            this.stop();
        }

        ball.setCenterY(y);
        ball.setCenterX(x);
    }
}

