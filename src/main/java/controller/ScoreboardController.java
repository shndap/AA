package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.User.User;
import model.User.Users;
import view.SettingMenu.ScoreboardMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ScoreboardController {
    private Pane pane;
    private VBox table;

    private Node person(User user, int level, int rank) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        String color = getColor(rank);
        int bestLevel = level == -1 ? user.getMaxLevel() + 1 : level + 1;
        double timeValue = user.getTimeLevel(level);
        String time = (timeValue == Double.POSITIVE_INFINITY ? "-" : Double.toString(timeValue));

        hBox.getChildren().addAll(getTopText("" + rank, 40, color),
                                  getUserPane(user, 200, color),
                                  getTopText("" + bestLevel, 75, color),
                                  getTopText("" + user.getScoreLevel(level), 75, color),
                                  getTopText(time, 75, color));
        return hBox;
    }

    private StackPane getUserPane(User user, int width, String color) {
        Text text = new Text(user.getUserName());
        Circle circle = new Circle(15);
        Circle background = new Circle(16);
        circle.setFill(new ImagePattern(ProfileMenuController.getImage(user)));
        background.setFill(Color.BLACK);

        StackPane newStackPane = new StackPane(background, circle);
        newStackPane.setAlignment(Pos.CENTER);


        HBox hBox = new HBox();
        Region rectangle = new Region();
        rectangle.setPrefSize(width, 40);
        rectangle.setStyle(
                "-fx-background-radius: 40;\n" +
                        "-fx-background-color: " + color + ";\n" +
                        "-fx-text-fill: white;\n"
        );
        text.setStyle("-fx-font: 14 System;");
        text.setFill(Color.WHITE);

        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(newStackPane, text);
        hBox.setMaxWidth(190);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, hBox);
        stackPane.setAlignment(Pos.CENTER);
        return stackPane;
    }

    private String getColor(int rank) {
        if (rank == 1) return "linear-gradient(to bottom, gold, darkgoldenrod)";
        if (rank == 2) return "linear-gradient(to bottom, silver, rgb(150, 150, 150))";
        if (rank == 3) return "linear-gradient(to bottom, rgb(205, 127, 50), rgb(154, 81, 37))";
        return "linear-gradient(to bottom, lightblue, cadetblue)";
    }

    private Node topRow() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(getTopText("#", 40, "darkblue"),
                                  getTopText("Name", 200, "darkblue"),
                                  getTopText("Level", 75, "darkblue"),
                                  getTopText("Score", 75, "darkblue"),
                                  getTopText("Time", 75, "darkblue"));
        return hBox;
    }

    private StackPane getTopText(String caption, int width, String color) {
        Text text = new Text(caption);
        Region rectangle = new Region();
        rectangle.setPrefSize(width, 40);
        rectangle.setStyle(
                "-fx-background-radius: 40;\n" +
                        "-fx-background-color: " + color + ";\n" +
                        "-fx-text-fill: white;\n"
        );
        text.setStyle("-fx-font: 14 System;");
        text.setFill(Color.WHITE);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, text);
        return stackPane;
    }


    public void initPane(int level) throws IOException {
        pane = getPaneFromFXML();
        VBox vBox = (VBox) pane.getChildren().get(0);
        table = (VBox) vBox.getChildren().get(1);
        HBox hBox = (HBox) table.getChildren().get(0);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(topRow());
        hBox.setSpacing(5);
        hBox.setPrefHeight(40);
        table.setSpacing(10);

        ArrayList<User> players = Users.getUsersByLevel(level);
        addPlayers(table, level, players);
    }

    private void addPlayers(VBox table, int level, ArrayList<User> users) {
        for (int rank = 1; rank <= Math.min(10, users.size()); rank++)
            table.getChildren().add(person(users.get(rank - 1), level, rank));
    }

    public Pane getPane() {
        return pane;
    }

    private Pane getPaneFromFXML() throws IOException {
        URL url = new URL(ScoreboardMenu.class.getResource("/view/Scoreboard/Scoreboard.fxml").toString());
        return FXMLLoader.load(Objects.requireNonNull(url));
    }
}
