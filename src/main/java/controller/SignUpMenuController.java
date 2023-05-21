package controller;

import model.RandomString;
import model.User.User;
import model.User.Users;

public class SignUpMenuController {
    public static boolean nameIsValid(String name) {
        return name.matches("^\\S+$");
    }

    public static boolean usernameExists(String name) {
        return Users.userExists(name);
    }

    public static boolean passIsValid(String pass) {
        return pass.length() >= 8 && pass.matches("\\S+");
    }

    public static void addUser(String name, String pass, boolean isGuest) {
        new User(name, pass, isGuest);
    }

    public static String addGuestUser() {
        String name;
        do {
            name = RandomString.getRandomString(13);
        } while (usernameExists(name));

        addUser(name, name, true);
        return name;
    }

    public static void removeUser(String toString) {
        Users.removeUser(toString);
    }

    public static boolean passMatches(String name, String pass) {
        return Users.passMatches(name, pass);
    }

    public static void removeCurrentUser() {
        removeUser(Users.getCurrentUser().getUserName());
    }

    public static void deleteCurrentAccount() {
        Users.setCurrentUserToNull();
        removeCurrentUser();
    }

    public static void logoutCurrentUser() {
        if (Users.getCurrentUser().isGuest()) removeCurrentUser();
        else Users.setCurrentUserToNull();
    }

    public static void setAvatar(String img) {
        Users.setCurrentAvatar(img);
    }
}
