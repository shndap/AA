package model.User;

import java.io.Serializable;

public enum Level implements Serializable {
    ONE(5, 1.2, 7),
    TWO(10, 1.5, 5),
    THREE(15, 1.8, 3);
    private final double speed;
    private final double windSpeed;
    private final double freezeSecs;

    Level(double speed, double windSpeed, double freezeSecs) {
        this.speed = speed;
        this.windSpeed = windSpeed;
        this.freezeSecs = freezeSecs;
    }

    public double getSpeed() {
        return speed;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getFreezeSecs() {
        return freezeSecs;
    }
}
