package model.User;

import controller.ControlKeys;
import javafx.scene.input.KeyCode;
import model.Maps;

import java.util.HashMap;

public class Preferance {
    public int level;
    public int numberOfBalls;
    public SoundMode soundMode;
    public ColorMode colorMode;
    public Language language;
    public HashMap<String, String> keys;
    public Maps defaultMap;

    public Preferance(int level,
                      int numberOfBalls,
                      SoundMode soundMode,
                      ColorMode colorMode,
                      Language language,
                      HashMap<String, String> keys,
                      Maps defaultMap) {
        this.level = level;
        this.numberOfBalls = numberOfBalls;
        this.soundMode = soundMode;
        this.colorMode = colorMode;
        this.language = language;
        this.keys = keys;
        this.defaultMap = defaultMap;
    }

    public static Preferance getDefaultPreference() {
        HashMap<String, String> map = new HashMap<>();
        map.put(ControlKeys.SHOOT1.getName(), KeyCode.SPACE.getName().toLowerCase());
        map.put(ControlKeys.FREEZE1.getName(), KeyCode.TAB.getName().toLowerCase());
        map.put(ControlKeys.LEFT1.getName(), KeyCode.LEFT.getName().toLowerCase());
        map.put(ControlKeys.RIGHT1.getName(), KeyCode.RIGHT.getName().toLowerCase());

        map.put(ControlKeys.SHOOT2.getName(), KeyCode.ENTER.getName().toLowerCase());
        map.put(ControlKeys.FREEZE2.getName(), KeyCode.TAB.getName().toLowerCase());
        map.put(ControlKeys.LEFT2.getName(), KeyCode.A.getName().toLowerCase());
        map.put(ControlKeys.RIGHT2.getName(), KeyCode.D.getName().toLowerCase());

        return new Preferance(2,
                              30,
                              SoundMode.UNMUTE,
                              ColorMode.COLORFUL,
                              Language.ENGLISH,
                              map,
                              Maps.DEFAULT);
    }

    public Level getLevel() {
        switch (level) {
            case 1 -> {
                return Level.ONE;
            }
            case 2 -> {
                return Level.TWO;
            }
            case 3 -> {
                return Level.THREE;
            }
            default -> throw new IllegalStateException("Unexpected value: " + level);
        }
    }

    public String getKeys() {
        String string = keys.toString();
        return string.replace(",\\S?", "\n");
    }
}
