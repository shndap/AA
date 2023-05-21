package view;

import controller.StarterController;
import view.OtherMenus.SignUpMenu;

import java.io.IOException;

public class Starter {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        StarterController.loadUsers();
        SignUpMenu.main(args);
        Runtime.getRuntime().addShutdownHook(new Thread(StarterController::saveUsers));
    }

}
