package model.Game;

import controller.GameMenuController;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.User.Level;
import model.User.User;

public class Game {
    private final CentralCircle centralCircle;
    private final FreezingBar freezingBar;
    private final Shooter shooter;
    private final Level level;
    private final DataBar dataBar;
    private boolean isLost;
    private boolean won;


    public Game(User user) {
        level = Level.values()[user.getLevel() - 1];
        centralCircle = new CentralCircle(user);
        freezingBar = new FreezingBar();
        shooter = new Shooter(user.getNumberOfBalls());
        isLost = false;
        won = false;
        dataBar = new DataBar(1, user.getLevel(), 0.00, 0);
    }

    public Game(User user, int level, double time, int score, int phase, CentralCircle centralCircle,
                FreezingBar freezingBar, Shooter shooter) {
        user.setLevel(level);
        this.level = Level.values()[user.getLevel() - 1];
        this.centralCircle = centralCircle;
        this.freezingBar = freezingBar;
        this.shooter = shooter;
        isLost = false;
        won = false;
        dataBar = new DataBar(phase, user.getLevel(), time, score);
    }

    static VBox getvBox(String caption, String name) {
        Text value = new Text(caption);
        value.setStyle("-fx-font: 25 System");
        Text text = new Text(name);
        text.setStyle("-fx-font: 30 System; -fx-font-weight: bold;");
        VBox vBox = new VBox(text, value);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    public DataBar getDataBar() {
        return dataBar;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }

    public Shooter getShooter() {
        return shooter;
    }

    public CentralCircle getCentralCircle() {
        return centralCircle;
    }

    public Level getLevel() {
        return level;
    }

    public Group getCentralGroup() {
        return centralCircle.getGroup();
    }

    public HBox getFreezingBar() {
        return freezingBar.gethBox();
    }

    public void setFreezingBar(double d) {
        freezingBar.setProgressBar(d);
    }

    public double getProgress() {
        return freezingBar.getProgressBar().getProgress();
    }

    public boolean barIsFull() {
        return freezingBar.isFull();
    }

    public Group getShooterHBox() {
        return shooter.getGroup();
    }

    public Circle addNewBall() {
        Circle ball = new Circle(8, Color.BLACK);
        GameMenuController.addBallToPane(ball, shooter.getCannonTopX(), shooter.getCannonTopY());
        return ball;
    }

    public void addFreezingPoint() {
        freezingBar.add(FreezingBar.oneBall);
    }

    public void won() {
        this.won = true;
    }

    public boolean hasWon() {
        return won;
    }

    public VBox getResultVBox() {
        return getResult();
    }

    private VBox getResult() {
        VBox values = getValues();

        Text state = getState();

        VBox result = new VBox(state, values);
        result.setAlignment(Pos.CENTER);
        result.setSpacing(30);
        return result;
    }

    private Text getState() {
        Text state = new Text(isLost ? "GAME OVER!" : "YOU WON!");
        state.setStyle("-fx-font: 65 System; -fx-font-weight: bold; -fx-fill: " +
                               (isLost ? "red" : "green"));
        return state;
    }

    private VBox getValues() {
        VBox score = getvBox(dataBar.getScoreString(), "Score");
        VBox time = getvBox(dataBar.getTimeString(), "Time");
        VBox level = getvBox(String.valueOf(getLevel().ordinal() + 1), "Level");
        VBox phase = getvBox(dataBar.getPhase(), "Phase");
        VBox values = new VBox(level, phase, score, time);
        values.setSpacing(15);
        values.setAlignment(Pos.CENTER);
        return values;
    }
}
