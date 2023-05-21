package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import model.User.User;
import model.User.Users;

public class ProfileMenuController {
    private final User user;

    public ProfileMenuController() {
        user = Users.getCurrentUser();
    }

    public static void setCurrentUser(String name) {
        Users.setCurrentUser(name);
    }

    public static void changeCurrentUsername(String newName) {
        User currentUser = Users.getCurrentUser();
        Users.removeUser(currentUser.getUserName());
        currentUser.setName(newName);
        Users.addUser(newName, currentUser);
    }

    public static String getCurrentPassword() {
        return Users.getCurrentUser().getPass();
    }

    public static void changeCurrentPass(String newPass) {
        Users.getCurrentUser().setPass(newPass);
    }

    public static String getCurrentUsername() {
        return Users.getCurrentUser().getUserName();
    }

    public static String getAvatar() {
        return Users.getCurrentAvatar();
    }

    public static Image getImage() {
        return new Image(getAvatar());
    }

    public static String getName() {
        return getCurrentUsername();
    }

    public static Image getImage(User user) {
        return new Image(user.getAvatar());
    }

    public static boolean isGuest() {
        return Users.getCurrentUser().isGuest();
    }

    public static Alert getControlKeysAlert() {
        return new Alert(Alert.AlertType.INFORMATION, Users.getCurrentUser().getPreferance().getKeys());
    }

    public static void removeGuests() {
        Users.removeGuests();
    }
}
