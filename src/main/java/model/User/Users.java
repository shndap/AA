package model.User;

import java.util.ArrayList;
import java.util.Arrays;
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
}
