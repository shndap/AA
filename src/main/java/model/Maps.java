package model;

import java.util.HashSet;
import java.util.Set;

public enum Maps {
    ORANGE(new HashSet<>(Set.of(0.0, 100.0, 120.0, 200.0, 220.0, 300.0, 320.0))),
    VOODOO(new HashSet<>(Set.of(20.0, 30.0, 40.0, 120.0, 130.0, 140.0, 220.0, 230.0, 240.0, 320.0, 330.0, 340.0))),
    RUDDER(new HashSet<>(Set.of(0.0, 60.0, 120.0, 180.0, 240.0, 300.0))),
    STAR(new HashSet<>(Set.of(0.0, 30.0, 60.0, 90.0, 120.0, 150.0, 180.0, 210.0, 240.0, 270.0, 300.0, 330.0))),
    DEFAULT(new HashSet<>(Set.of(0.0, 10.0, 20.0, 30.0, 40.0, 50.0))),
    CROSS(new HashSet<>(Set.of(355.0, 5.0, 85.0, 95.0, 175.0, 185.0, 265.0, 275.0)));

    private final HashSet<Double> initBalls;

    Maps(HashSet<Double> initBalls) {
        this.initBalls = initBalls;
    }

    public HashSet<Double> getBalls() {
        return initBalls;
    }
}
