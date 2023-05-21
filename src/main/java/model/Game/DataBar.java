package model.Game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.Serializable;

public class DataBar implements Serializable {
    private final VBox phase;
    private final VBox level;
    private final VBox time;
    private final VBox score;
    private final HBox hBox;
    private final long startTime;
    private Timeline timeline;

    public DataBar(int phase, int level, double time, int score) {
        startTime = System.currentTimeMillis();
        this.phase = getVBox("" + phase, "Phase");
        this.level = getVBox("" + level, "Level");
        this.time = getVBox("%.2f".formatted(time), "Time");
        this.score = getVBox("" + score, "Score");
        hBox = new HBox(this.level, this.phase, this.time, this.score);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
    }

    private void addTimeHandler() {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            Text text = (Text) time.getChildren().get(1);
            text.setText("%.2f".formatted((System.currentTimeMillis() - startTime) / 1000.0));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void firstShoot() {
        addTimeHandler();
    }

    public HBox gethBox() {
        return hBox;
    }

    private VBox getVBox(String caption, String name) {
        Text value = new Text(caption);
        value.setStyle("-fx-font: 14 System");
        Text text = new Text(name);
        text.setStyle("-fx-font: 14 System; -fx-font-weight: bold");
        VBox vBox = new VBox(text, value);
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    public void stop() {
        timeline.stop();
    }

    public void addScore() {
        Text text = (Text) score.getChildren().get(1);
        int add = Integer.parseInt(((Text) phase.getChildren().get(1)).getText());
        text.setText(String.valueOf(Integer.parseInt(text.getText()) + add));
    }

    public int getScore() {
        return Integer.parseInt(((Text) score.getChildren().get(1)).getText());
    }

    public String getScoreString() {
        return ((Text) score.getChildren().get(1)).getText();
    }

    public double getTime() {
        return Double.parseDouble(((Text) time.getChildren().get(1)).getText());
    }

    public String getTimeString() {
        double time1 = getTime();
        String minute = String.valueOf((int) (time1 / 60));
        String second = String.valueOf((int) (time1 % 60));
        String milis = String.valueOf((int) ((time1 % 1.00) * 100));

        minute = addPrecedingZeros(minute);
        second = addPrecedingZeros(second);
        milis = addPrecedingZeros(milis);

        return minute + ":" + second + "." + milis;
    }

    private String addPrecedingZeros(String value) {
        if (value.length() > 2) {
            return "%s%s".formatted(value.charAt(0), value.charAt(1));
        }
        String out = "";
        for (int i = 0; i < (2 - value.length()); i++)
            out += "0";
        out += value;

        return out;
    }

    public String getPhase() {
        return ((Text) phase.getChildren().get(1)).getText();
    }

    public void setPhase(int phaseNo) {
        Text text = (Text) phase.getChildren().get(1);
        text.setText(phaseNo + "");
    }
}
