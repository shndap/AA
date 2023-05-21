package controller;

import model.Saver.UserSaver;

import java.io.IOException;

public class StarterController {
    public static void saveUsers() {
        UserSaver userSaver = new UserSaver();
        try {
            userSaver.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadUsers() throws IOException, ClassNotFoundException {
        UserSaver lastSave = UserSaver.getLastSave();
        if (lastSave == null) return;
        lastSave.load();
    }
}
