package model.Game;

import controller.GameMenuController;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.User.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class CentralCircle implements Serializable {
    private final User user;
    private final Circle innerCircle;
    private final Circle outerCircle;
    private final HashMap<Double, Circle> balls;
    private Group group;
    private long startTimeInMilis;

    public CentralCircle(User user) {
        innerCircle = new Circle(50);
        outerCircle = new Circle(150);
        outerCircle.setFill(Color.TRANSPARENT);
        this.balls = new HashMap<>();
        this.user = user;

        initializeMap();
    }

    public CentralCircle(User user, HashSet<Double> customBalls, double circleAngle) {
        innerCircle = new Circle(50);
        outerCircle = new Circle(150);
        outerCircle.setFill(Color.TRANSPARENT);
        balls = new HashMap<>();
        this.user = user;

        initializeMap(customBalls);
        group.setRotate(circleAngle);
    }

    public HashSet<Double> getBalls() {
        return new HashSet<>(balls.keySet());
    }

    public Circle getInnerCircle() {
        return innerCircle;
    }

    public Group getGroup() {
        return group;
    }

    private void initializeMap() {
        group = new Group();
        group.getChildren().add(outerCircle);
        for (Double d : user.getMap().getBalls())
            addBall(d);
        group.getChildren().add(innerCircle);
    }

    private void initializeMap(HashSet<Double> customBalls) {
        group = new Group();
        group.getChildren().add(outerCircle);
        for (Double d : customBalls)
            addBall(d);
        group.getChildren().add(innerCircle);
    }

    private void addBall(Double d) {
        Circle ball = getBall(d);
        balls.put(d, ball);
        Line line = getLine(ball);
        group.getChildren().addAll(line, ball);
    }

    private Line getLine(Circle ball) {
        Line line = new Line();
        line.setStartX(innerCircle.getCenterX());
        line.setStartY(innerCircle.getCenterY());
        line.setEndX(ball.getCenterX());
        line.setEndY(ball.getCenterY());
        return line;
    }

    private Circle getBall(Double d) {
        Circle circle = new Circle(8, Color.BLACK);
        circle.setCenterX(innerCircle.getCenterX() + outerCircle.getRadius() * Math.sin(Math.toRadians(d)));
        circle.setCenterY(innerCircle.getCenterY() + outerCircle.getRadius() * Math.cos(Math.toRadians(d)));
        return circle;
    }

    public double getX() {
        return innerCircle.getCenterX();
    }

    public double getY() {
        return innerCircle.getCenterY();
    }

    public boolean intersects(Circle ball) {
        for (Circle circle : balls.values())
            if (ball.localToScene(ball.getBoundsInLocal()).intersects(circle.localToScene(circle.getBoundsInParent())) &&
                    !ball.equals(circle))
                return true;
        return false;
    }

    public boolean canBePlaced(Circle ball) {
        if (intersects(ball)) return false;

        double x = ball.localToScene(ball.getBoundsInLocal()).getCenterX() -
                innerCircle.localToScene(innerCircle.getBoundsInLocal()).getCenterX();
        double y = ball.localToScene(ball.getBoundsInLocal()).getCenterY() -
                innerCircle.localToScene(innerCircle.getBoundsInLocal()).getCenterY();
        double r = Math.sqrt(x * x + y * y);

        return Math.abs(r - outerCircle.getRadius()) <= 5;
    }

    public void addBall(Circle ball, double rotAngle, Pane pane) {
        double centerX = ball.localToScene(ball.getBoundsInLocal()).getCenterX();
        double centerY = ball.localToScene(ball.getBoundsInLocal()).getCenterY();
        double angle = (getAngle(centerX, centerY) + rotAngle) % 360;

        if(pane != null) pane.getChildren().remove(ball);
        addBall(angle);
        GameMenuController.addFreezingPoint();
        coverLines();
    }

    private void coverLines() {
        group.getChildren().remove(innerCircle);
        group.getChildren().add(innerCircle);
    }

    private double getAngle(double X, double Y) {
        double x = X - innerCircle.localToScene(innerCircle.getBoundsInLocal()).getCenterX();
        double y = Y - innerCircle.localToScene(innerCircle.getBoundsInLocal()).getCenterY();

        double r = Math.sqrt(x * x + y * y);

        double acos = Math.acos(y / r);
        return Math.toDegrees(x / y > 0 ? acos : -acos);
    }

    public void startPulsating() {
        for (Circle ball : balls.values()) Phase2.addPulsation(this, ball);
    }


    public Circle getOuterCircle() {
        return outerCircle;
    }

    public long getStartTimeInMilis() {
        return startTimeInMilis;
    }

    public void setStartTimeInMilis(long startTimeInMilis) {
        this.startTimeInMilis = startTimeInMilis;
    }
}
