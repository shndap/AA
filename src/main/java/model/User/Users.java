package model.User;

import model.Saver.GameSaver;
import view.GameMenu.PauseMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Users {
    private static final LinkedHashMap<String, User> users;
    private static User currentUser;

    static {
        users = new LinkedHashMap<>();
    }

    public static boolean userExists(String name) {
        return users.containsKey(name);
    }

    public static void addUser(String name, User user) {
        users.put(name, user);
    }

    public static void removeUser(String name) {
        users.remove(name);
    }

    public static User getUser(String name) {
        return users.get(name);
    }

    public static boolean passMatches(String name, String pass) {
        return users.get(name).passMatches(pass);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String name) {
        Users.currentUser = getUser(name);
    }

    public static void setCurrentUserToNull() {
        currentUser = null;
    }

    public static String getCurrentAvatar() {
        return currentUser.getAvatar();
    }

    public static void setCurrentAvatar(String img) {
        currentUser.setAvatar(img);
    }

    public static ArrayList<User> getUsersByLevel(int level) {
        User[] array = users.values().toArray(new User[0]);
        Arrays.sort(array, User.compareByLevel[level + 1]);
        return new ArrayList<>(Arrays.stream(array).toList());
    }

    public static boolean isGuest(String username) {
        return users.get(username).isGuest();
    }

    public static void removeGuests() {
        ArrayList<String> toRemove = new ArrayList<>();
        for(User user : users.values())
            if(user.isGuest())
                toRemove.add(user.getUserName());
        for(String username : toRemove) {
            removeUser(username);
            File file = new File(GameSaver.getGameSavePath(username));
            if(file.exists())
                file.delete();
        }
    }

    public static HashSet<User> getAllUsers() {
        return new HashSet<>(users.values());
    }
}
