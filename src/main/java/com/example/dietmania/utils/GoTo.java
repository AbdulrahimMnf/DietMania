package com.example.dietmania.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GoTo {

    private static final String FXML_BASE =
            "/com/example/dietmania/";   // change once, use everywhere

    public static void page(Node source, String fxml) {

        try {
            URL location = GoTo.class.getResource(FXML_BASE + fxml);

            if (location == null) {
                throw new RuntimeException(
                        "FXML not found: " + FXML_BASE + fxml
                );
            }

            Parent root = FXMLLoader.load(location);

            Stage stage = (Stage) source
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
