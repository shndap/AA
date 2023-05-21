module aa.aa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens view to javafx.fxml;
    exports view;
    exports model;
    opens model to javafx.fxml;
    exports model.Game;
    opens model.Game to javafx.fxml;
    exports model.Animations;
    opens model.Animations to javafx.fxml;
    exports model.User;
    opens model.User to javafx.fxml;
    exports view.ProfileMenu;
    opens view.ProfileMenu to javafx.fxml;
    exports view.SettingMenu;
    opens view.SettingMenu to javafx.fxml;
    exports view.GameMenu;
    opens view.GameMenu to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}