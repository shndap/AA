package model.Game;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.io.Serializable;

public class Shooter implements Serializable {
    private final Circle circle;
    private double angle;
    private int balls;
    private Group group;
    private Line cannon;
    private Text text;
    private boolean rotation;
    private Text angleText;

    public Shooter(int balls) {
        this.balls = balls;
        circle = new Circle(30, Color.BLACK);
        angle = 0;
        initText(balls);
        initCannon();
        initAngleText();
        initGroup();
        rotation = false;
    }

    private void initAngleText() {
        angleText = new Text(String.valueOf(angle));
        angleText.setStyle("-fx-font: 12 System; -fx-fill: lightblue; -fx-font-weight: bold");
    }

    private void initGroup() {
        Circle circle1 = new Circle(30, Color.TRANSPARENT);
        StackPane stackPane = new StackPane(circle1, text, angleText);
        stackPane.setLayoutX(circle.getCenterX() - circle.getRadius());
        stackPane.setLayoutY(circle.getCenterY() - circle.getRadius());
        StackPane.setAlignment(angleText, Pos.BOTTOM_CENTER);

        group = new Group(cannon, circle, stackPane);
    }

    private void initText(int balls) {
        text = new Text(String.valueOf(balls));
        setTextColor(Color.RED);
    }

    private void initCannon() {
        cannon = new Line();
        cannon.setStrokeWidth(10);
        cannon.setStrokeType(StrokeType.CENTERED);
        cannon.setStrokeLineCap(StrokeLineCap.ROUND);
        cannon.setStartX(circle.getCenterX());
        cannon.setStartY(circle.getCenterY());
        cannon.setEndX(cannon.getStartX() + circle.getRadius() * 1.5 * Math.sin(Math.toRadians(angle)));
        cannon.setEndY(cannon.getStartY() - circle.getRadius() * 1.5 * Math.cos(Math.toRadians(angle)));
    }

    public Group getGroup() {
        return group;
    }

    public void reduceBalls() {
        balls--;
        text.setText(String.valueOf(balls));
    }

    public double getCannonTopX() {
        return cannon.localToScene(cannon.getBoundsInLocal()).getMaxX() - cannon.getStrokeWidth() / 2;
    }

    public double getCannonTopY() {
        return cannon.localToScene(cannon.getBoundsInLocal()).getMinY();
    }

    public int getNumberOfBalls() {
        return balls;
    }

    public void moveLeft() {
        if (group.getLayoutX() >= 50)
            group.setLayoutX(group.getLayoutX() - 10);
    }

    public void moveRight() {
        if (group.getLayoutX() <= 450)
            group.setLayoutX(group.getLayoutX() + 10);
    }

    public double getCannonAngle() {
        return angle;
    }

    public void setCannonAngle(double d) {
        group.getChildren().remove(cannon);
        angle = d;
        initCannon();
        group.getChildren().add(0, cannon);
        String s = String.valueOf(angle + 0.000001);
        int end = s.indexOf('.');
        angleText.setText(s.substring(0, end + 3));
    }

    public void addRotation() {
        this.rotation = true;
    }

    public boolean hasRotation() {
        return rotation;
    }

    public void setTextColor(Color color) {
        String rgb = String.format("rgb(%d, %d, %d)",
                                   (int) (color.getRed() * 255.0),
                                   (int) (color.getGreen() * 255.0),
                                   (int) (color.getBlue() * 255.0));
        text.setStyle("-fx-font: 30 Arial; ; -fx-font-weight: bold; -fx-fill: " + rgb);
    }

    public double getLayoutX() {
        return group.getLayoutX();
    }

    public void setLayoutX(double shooterX) {
        group.setLayoutX(shooterX);
    }
}
