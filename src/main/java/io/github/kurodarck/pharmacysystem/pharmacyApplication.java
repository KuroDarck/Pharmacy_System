package io.github.kurodarck.pharmacysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class pharmacyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(pharmacyApplication.class.getResource("/io/github/kurodarck/pharmacysystem/view/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Image/icon.png"))));
        stage.setTitle("Pharmacy System");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
