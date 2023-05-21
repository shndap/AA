package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Animations.FreezingAnimation;
import model.Animations.ShootingAnimation;
import model.Game.Game;
import model.Game.Phase2;
import model.Game.Phase3;
import model.Game.Phase4;
import model.Saver.GameSaver;
import model.User.Users;
import view.GameMenu.GameResultMenu;
import view.OtherMenus.SignUpMenu;

import java.io.IOException;

public class GameMenuController {
    private static Game game;
    private static Timeline rotation;
    private static double lastFreeze;
    private static Pane pane;
    private static boolean isInverse;
    private static boolean toLeft;
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void createNewGame() {
        game = new Game(Users.getCurrentUser());
        isInverse = false;
    }

    public static void createNewGame(Game game, boolean isInverse, boolean toLeft) {
        GameMenuController.game = game;
        GameMenuController.isInverse = isInverse;
        GameMenuController.toLeft = toLeft;
        //todo: handle shit
    }

    public static Group centralCircle() {
        return game.getCentralGroup();
    }

    public static double getDuration() {
        return 30000.0 / game.getLevel().getSpeed();
    }

    public static void startRotating() {
        Rotate rotate = new Rotate(0, game.getCentralCircle().getX(), game.getCentralCircle().getY());
        centralCircle().getTransforms().add(rotate);

        rotation = new Timeline();
        rotation.getKeyFrames().add(new KeyFrame(
                Duration.millis(getDuration()),
                new KeyValue(rotate.angleProperty(), 360)
        ));

        rotation.setCycleCount(-1);
        rotation.play();
        rotation.setRate(isInverse ? 1 : -1);
    }

    public static void stopRotating() {
        rotation.stop();
    }

    public static void freeze() {
        rotation.setRate(0.2 * rotation.getRate());

        lastFreeze = System.currentTimeMillis();
        FreezingAnimation.getFreezingAnimation().play();
        FreezingAnimation.freezingBarAnimation().play();
    }

    public static void freeze(double duration) {
        rotation.setRate(0.2 * rotation.getRate());

        lastFreeze = System.currentTimeMillis() + duration - getFreezeDuration();
        FreezingAnimation.getFreezingAnimation(duration).play();
        FreezingAnimation.freezingBarAnimation(duration).play();
    }

    public static void continueRotation() {
        rotation.setRate(5 * rotation.getRate());
    }

    public static double getFreezeDuration() {
        return game.getLevel().getFreezeSecs() * 1000.0;
    }

    public static boolean isFrozen() {
        return System.currentTimeMillis() - lastFreeze <= getFreezeDuration();
    }

    public static double getRotatedAngle() {
        double xx = centralCircle().getLocalToSceneTransform().getMxx();
        double xy = centralCircle().getLocalToSceneTransform().getMxy();
        return Math.atan2(-xy, xx);
    }


    public static Game getGame() {
        return game;
    }

    public static HBox getFreezingBarHbox() {
        return game.getFreezingBar();
    }

    public static void setFreezingBar(double d) {
        game.setFreezingBar(d);
    }

    public static boolean barIsFull() {
        return game.barIsFull();
    }

    public static Group getShooter() {
        return game.getShooterHBox();
    }

    public static void shoot() {
        if (isFirstShoot())
            game.getDataBar().firstShoot();

        game.getShooter().reduceBalls();
        Circle ball = game.addNewBall();
        ShootingAnimation shootingAnimation = new ShootingAnimation(pane,
                                                                    game.getShooter(),
                                                                    ball,
                                                                    game.getCentralCircle());
        shootingAnimation.play();
    }

    private static boolean isFirstShoot() {
        return game.getShooter().getNumberOfBalls() == Users.getCurrentUser().getNumberOfBalls();
    }

    public static void lost() throws Exception {
        game.setLost(true);
        stopRotating();
        if (pane != null)
            pane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

        removeFromPane();
    }

    private static void removeFromPane() throws Exception {
        if (pane == null) return;
        pane.getChildren().remove(game.getCentralGroup());
        pane.getChildren().remove(game.getShooterHBox());
        stopActivities();
        pane = null;

        Users.getCurrentUser().addScoreByGame(game);

        showGameResult();
    }

    private static void showGameResult() throws Exception {
        GameResultMenu gameResultMenu = new GameResultMenu();
        gameResultMenu.setGame(game);
        gameResultMenu.start(SignUpMenu.stage);
    }

    public static void won() throws Exception {
        game.won();
        stopRotating();
        pane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        removeFromPane();
    }

    public static void addBall(Circle ball) {
        game.getCentralCircle().addBall(ball, Math.toDegrees(getRotatedAngle()), pane);
    }

    public static void addBallToPane(Circle ball, double cannonTopX, double cannonTopY) {
        pane.getChildren().add(ball);
        ball.setCenterX(cannonTopX);
        ball.setCenterY(cannonTopY);
    }

    public static void setStackPane(Pane pane) {
        GameMenuController.pane = pane;
    }

    public static void addFreezingPoint() {
        game.addFreezingPoint();
    }

    public static HBox getDataBar() {
        return game.getDataBar().gethBox();
    }

    public static void startPhase2() {
        game.getShooter().setTextColor(Color.ORANGE);
        Phase2.newPhase();
        Phase2.getRotationTimeLine().play();
        game.getDataBar().setPhase(2);
        pulsatingBalls();
    }

    public static void startPhase3() {
        game.getShooter().setTextColor(Color.YELLOW);
        game.getDataBar().setPhase(3);
        game.getCentralCircle().setStartTimeInMilis(System.currentTimeMillis());
        Phase3.newPhase();
        Phase3.getTimeline(game.getCentralCircle()).play();
    }

    public static void startPhase4() {
        game.getShooter().setTextColor(Color.LIGHTGREEN);
        toLeft = true;
        game.getDataBar().setPhase(4);
        Phase4.newPhase();
        Phase4.startPhase4(pane.getScene(), game.getShooter(), Users.getCurrentUser().getPreferance());
    }

    public static boolean shouldStartPhase2() {
        return game.getShooter().getNumberOfBalls() == Users.getCurrentUser().getNumberOfBalls() * 3 / 4;
    }

    public static boolean shouldStartPhase3() {
        return game.getShooter().getNumberOfBalls() == Users.getCurrentUser().getNumberOfBalls() / 2;
    }

    public static boolean shouldStartPhase4() {
        return game.getShooter().getNumberOfBalls() == Users.getCurrentUser().getNumberOfBalls() / 4;
    }

    public static void reverseRotation() {
        rotation.play();
        rotation.setRate(-rotation.getRate());
        isInverse = !isInverse;
    }

    public static void pulsatingBalls() {
        game.getCentralCircle().startPulsating();
    }

    public static boolean getToLeft() {
        return toLeft;
    }

    public static void setToLeft(boolean b) {
        toLeft = b;
    }

    public static double getWindSpeed() {
        return Users.getCurrentUser().getPreferance().getLevel().getWindSpeed();
    }

    public static boolean finished() {
        return game.getShooter().getNumberOfBalls() == 0;
    }

    public static boolean cannotPlay() {
        return game.isLost() || game.hasWon();
    }

    private static void stopActivities() {
        rotation.stop();
        Phase2.stop();
        Phase3.stop();
        Phase4.stop();
        //todo: ball
    }

    public static void saveGame() throws IOException {
        stopActivities();

        new GameSaver(game, isInverse, toLeft);
    }

    public static void loadGame() throws IOException, ClassNotFoundException {
        GameSaver gameSaver = GameSaver.getSavedGamed(Users.getCurrentUser().getUserName());

        isInverse = gameSaver.isInverse();
        toLeft = gameSaver.isToLeft();;
    }

    public static void setInverse(boolean isInverse) {
        GameMenuController.isInverse = isInverse;
    }

    public static double getLastFreeze() {
        return lastFreeze;
    }
}
