package com.example.dietmania.Controllers;


import com.example.dietmania.Models.User;
import com.example.dietmania.Services.Auth.Login;
import com.example.dietmania.Services.Settings.SessionManager;
import com.example.dietmania.utils.GoTo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class LoginController {




    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private void handleLogin(ActionEvent event) {

        String email = emailField.getText().trim();
        String password = passwordField.getText();


        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter email and password");
            return;
        }

        Login login = new Login();
        User user = null;

        try {
            user = login.login(email,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (user != null) {
            SessionManager.saveUser(user);
            GoTo.page(
                    (Node) event.getSource(),
                    "main.fxml"
            );
        } else {
            showAlert("Error", "Invalid email or password");
        }
    }



    @FXML
    protected void handleRegister(ActionEvent event) throws IOException {
        GoTo.page(
                (Node) event.getSource(),
                "register.fxml"
        );
    }





    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
        System.out.println("Titiklendi");
    }


}

