package model.Saver;

import controller.GameMenuController;
import model.Game.*;
import model.User.Level;
import model.User.Users;
import view.GameMenu.GameMenu;

import java.io.*;
import java.util.HashSet;

public class GameSaver implements Serializable {
    private boolean isInverse;
    private boolean toLeft;
    private HashSet<Double> balls;
    private double circleAngle;
    private double progress;
    private double cannonAngle;
    private int shooterBalls;
    private double shooterX;
    private Level level;
    private double time;
    private int phase;
    private int score;

    public GameSaver(Game game, boolean isInverse, boolean toLeft) throws IOException {
        this.isInverse = isInverse;
        this.toLeft = toLeft;
        balls = game.getCentralCircle().getBalls();
        circleAngle = GameMenuController.getRotatedAngle();
        progress = game.getProgress();
        cannonAngle = game.getShooter().getCannonAngle();
        shooterBalls = game.getShooter().getNumberOfBalls();
        level = game.getLevel();
        time = game.getDataBar().getTime();
        phase = Integer.parseInt(game.getDataBar().getPhase());
        score = game.getDataBar().getScore();
        shooterX = game.getShooter().getLayoutX();

        serialize(Users.getCurrentUser().getUserName());
    }

    private static String getGameSavePath(String username) {
        return "src/main/resources/view/Database/Games/" + username + ".aas";
    }

    public static GameSaver getSavedGamed(String username) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(getGameSavePath(username));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        GameSaver gameSaver = (GameSaver) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return gameSaver;
    }

    public boolean isInverse() {
        return isInverse;
    }

    public boolean isToLeft() {
        return toLeft;
    }

    private void serialize(String username) throws IOException {
        String path = getGameSavePath(username);

        File aasFile = new File(path);

        if (aasFile.exists()) new PrintWriter(path).close();
        else aasFile.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

        outputStream.writeObject(this);

        outputStream.close();
        fileOutputStream.close();
    }

    public GameMenu load() {
        GameMenuController.createNewGame();

        GameMenuController.setToLeft(toLeft);
        GameMenuController.setInverse(isInverse);

        CentralCircle centralCircle = new CentralCircle(Users.getCurrentUser(), balls, circleAngle);

        FreezingBar freezingBar = new FreezingBar();
        freezingBar.setProgressBar(progress);

        Shooter shooter = new Shooter(shooterBalls);
        shooter.setCannonAngle(cannonAngle);
        shooter.setLayoutX(shooterX);

        Game game = new Game(Users.getCurrentUser(), level.ordinal() + 1, time, score, phase,
                             centralCircle, freezingBar, shooter);

        GameMenu gameMenu = new GameMenu();
        return gameMenu;
    }
}
