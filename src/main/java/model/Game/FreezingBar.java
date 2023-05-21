package model.Game;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.Serializable;

public class FreezingBar implements Serializable {
    public static double oneBall = 0.1;
    private ProgressBar progressBar;
    private HBox hBox;
    private Text text;

    public FreezingBar() {
        setProgressBar();
        setText();
        setHBox();
    }

    private void setHBox() {
        hBox = new HBox(text, progressBar);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
    }

    private void setText() {
        text = new Text("");
        text.setStyle("-fx-font: 15 System;");
    }

    private void setProgressBar() {
        progressBar = new ProgressBar();
        progressBar.setPrefSize(170, 25);
        progressBar.setProgress(1.0);
        progressBar.setStyle("-fx-accent: lightblue");
    }

    public void add(double d) {
        progressBar.setProgress(Math.min(progressBar.getProgress() + d, 1.0));
    }

    public boolean isFull() {
        return progressBar.getProgress() >= 1.0;
    }

    public HBox gethBox() {
        return hBox;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(double d) {
        if (d >= 1.0) d = 1.0;
        else if (d <= 0.0) d = 0.0;
        progressBar.setProgress(d);
    }
}
