package model.Saver;

import model.User.User;
import model.User.Users;

import java.io.*;
import java.util.HashSet;

public class UserSaver implements Serializable {
    public static final String path = "src/main/resources/view/Database/Users.udb";
    private HashSet<User> users;

    public UserSaver() {
        users = Users.getAllUsers();
    }

    public static UserSaver getLastSave() throws IOException, ClassNotFoundException {
        File file = new File(path);

        if (!file.exists()) return null;

        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        UserSaver userSaver = (UserSaver) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();

        return userSaver;
    }

    public void save() throws IOException {
        File udbFile = new File(path);

        if (udbFile.exists()) new PrintWriter(path).close();
        else udbFile.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

        outputStream.writeObject(this);

        outputStream.close();
        fileOutputStream.close();
    }

    public void load() {
        for (User user : users)
            if (!Users.userExists(user.getUserName()))
                Users.addUser(user.getUserName(), user);
    }
}
