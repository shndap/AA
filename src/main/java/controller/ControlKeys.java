package controller;

public enum ControlKeys {
    SHOOT1("shoot1"),
    SHOOT2("shoot2"),
    FREEZE1("freeze1"),
    FREEZE2("freeze2"),
    LEFT1("left1"),
    LEFT2("left2"),
    RIGHT1("right1"),
    RIGHT2("right2");
    public String keyName;

    ControlKeys(String keyName) {
        this.keyName = keyName;
    }

    public String getName() {
        return keyName;
    }
}
