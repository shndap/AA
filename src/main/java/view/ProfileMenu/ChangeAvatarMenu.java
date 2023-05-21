package view.ProfileMenu;

import controller.ProfileMenuController;
import controller.SignUpMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.OtherMenus.SignUpMenu;

import java.io.File;

public class ChangeAvatarMenu extends Application {
    public Text name;
    public Circle circle;


    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(ChangeAvatarMenu.class.getResource("/view/ProfileMenu/ChangeAvatarMenu/ChangeAvatarMenu.fxml"));

        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        name.textProperty().set(ProfileMenuController.getCurrentUsername());
        circle.setFill(new ImagePattern(ProfileMenuController.getImage()));
    }

    public void premadeAvatars() throws Exception {
        new PremadeAvatarMenu().start(SignUpMenu.stage);
    }

    public void fromFiles() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick an avatar");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg)",
                                                                                      "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(SignUpMenu.stage);
        String path = file.getPath();
        SignUpMenuController.setAvatar(path);
        this.start(SignUpMenu.stage);
    }

    public void back() throws Exception {
        new ProfileMenu().start(SignUpMenu.stage);
    }
}
