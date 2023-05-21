package model.User;

import model.Game.Game;
import model.Maps;
import view.ProfileMenu.PremadeAvatarMenu;

import java.io.Serializable;
import java.util.Comparator;

public class User implements Serializable {
    public static Comparator[] compareByLevel;

    static {
        compareByLevel = new Comparator[]{
                (Comparator<User>) User::compare,
                (Comparator<User>) User::compare1,
                (Comparator<User>) User::compare2,
                (Comparator<User>) User::compare3
        };
    }

    private final boolean isGuest;
    private final Preferance preferance;
    private final int[] score;
    private final double[] time;
    private String name;
    private String pass;
    private String avatar;

    public User(String name, String pass, boolean isGuest) {
        this.name = name;
        this.pass = pass;
        this.isGuest = isGuest;
        Users.addUser(name, this);
        avatar = RandomAvatar();
        score = new int[]{0, 0, 0};
        time = new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
        preferance = Preferance.getDefaultPreference();
    }

    private static int compare3(User o1, User o2) {
        if (o1.score[2] == o2.score[2]) return Double.compare(o1.time[2], o2.time[2]);
        return -Integer.compare(o1.score[2], o2.score[3]);
    }

    private static int compare2(User o1, User o2) {
        if (o1.score[1] == o2.score[1]) return Double.compare(o1.time[1], o2.time[1]);
        return -Integer.compare(o1.score[1], o2.score[1]);
    }

    private static int compare1(User o1, User o2) {
        if (o1.score[0] == o2.score[0]) return Double.compare(o1.time[0], o2.time[0]);
        return -Integer.compare(o1.score[0], o2.score[0]);
    }

    private static int compare(User o1, User o2) {
        if (o1.getMaxLevel() == o2.getMaxLevel() && o1.getScoreLevel(-1) == o2.getScoreLevel(-1)) {
            return Double.compare(o1.time[o1.getMaxLevel()], o2.time[o2.getMaxLevel()]);
        }
        return -Integer.compare(o1.getScoreLevel(-1), o2.getScoreLevel(-1));
    }

    public Preferance getPreferance() {
        return preferance;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String img) {
        avatar = img;
    }

    private String RandomAvatar() {
        int i = (int) Math.ceil(Math.random() * (double) 16);
        return PremadeAvatarMenu.class.getResource(
                "/view/ProfileMenu/ChangeAvatarMenu/Avatars/256_" + i + ".png").toExternalForm();
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean passMatches(String pass) {
        return this.pass.equals(pass);
    }

    public String getUserName() {
        return name;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public int getScoreLevel(int level) {
        if (level == -1) return Math.max(score[0], Math.max(score[1], score[2]));
        return score[level];
    }

    public int getMaxLevel() {
        int maxScore = -1;
        int index = -1;

        for (int i = 0; i < 3; i++) {
            if (score[i] >= maxScore) {
                index = i;
                maxScore = score[i];
            }
        }

        return index;
    }

    public Double getTimeLevel(int level) {
        return level == -1 ? time[getMaxLevel()] : time[level];
    }

    public SoundMode getMute() {
        return preferance.soundMode;
    }

    public void setMute(SoundMode soundMode) {
        preferance.soundMode = soundMode;
    }

    public ColorMode getBW() {
        return preferance.colorMode;
    }

    public void setBW(ColorMode colorMode) {
        preferance.colorMode = colorMode;
    }

    public Language getLanguage() {
        return preferance.language;
    }

    public void setLanguage(Language language) {
        preferance.language = language;
    }

    public int getLevel() {
        return preferance.level;
    }

    public void setLevel(int level) {
        preferance.level = level;
    }

    public int getNumberOfBalls() {
        return preferance.numberOfBalls;
    }

    public void setNumberOfBalls(int balls) {
        preferance.numberOfBalls = balls;
    }

    public Maps getMap() {
        return preferance.defaultMap;
    }

    public void setMap(Maps maps) {
        preferance.defaultMap = maps;
    }

    public String getKey(String key) {
        return preferance.keys.get(key);
    }

    public void addScoreByGame(Game game) {
        int level = game.getLevel().ordinal();
        int gameScore = game.getDataBar().getScore();
        double gameTime = game.getDataBar().getTime();

        if (score[level] < gameScore) {
            score[level] = gameScore;
            time[level] = gameTime;
        } else if (score[level] == gameScore)
            time[level] = gameTime;
    }

    public void setKey(String buttonName, String key) {
        preferance.keys.replace(buttonName, key);
    }

}
