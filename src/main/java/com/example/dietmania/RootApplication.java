package com.example.dietmania;

import com.example.dietmania.Services.Settings.SessionManager;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RootApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        splashScreen();
    }

    public static void main(String[] args) {
        launch();
    }

    // Splash Screen by image vıew
    private  void splashScreen()
    {
        Image splashImage = new Image(getClass().getResource("images/splash.jpg").toExternalForm());
        ImageView imageView = new ImageView(splashImage);
        imageView.setFitWidth(1080);
        imageView.setFitHeight(720);
        imageView.setPreserveRatio(true);

        StackPane root = new StackPane(imageView);
        Scene splashScene = new Scene(root, 1080, 720);

        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.UNDECORATED);
        splashStage.setScene(splashScene);
        splashStage.centerOnScreen();
        // app icon
        Image icon = new Image(getClass().getResource("images/icon.png").toExternalForm());
        splashStage.getIcons().add(icon);

        splashStage.show();

        // Simulate loading
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(3000);
                return null;
            }
        };

        sleeper.setOnSucceeded(event -> {
            splashStage.close();
            navigateToNextScreen();

        });

        new Thread(sleeper).start();
    }


    private void navigateToNextScreen()  {

        try {
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setWidth(1080);
            stage.setHeight(720);
            stage.centerOnScreen();
            // app icon
            Image icon = new Image(getClass().getResource("images/icon.png").toExternalForm());
            stage.getIcons().add(icon);
            //----------------

            String fxmlFile;

            // eger kullanici zaten giris yapmis kontrolu icin  yoksa Login ekranina gonder
            if (SessionManager.hasSession()) {
                fxmlFile = "main.fxml";
            } else {
                fxmlFile = "login.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene mainScene = new Scene(loader.load());
            stage.setScene(mainScene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }





}
