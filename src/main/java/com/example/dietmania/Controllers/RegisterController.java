package com.example.dietmania.Controllers;


import com.example.dietmania.Models.User;
import com.example.dietmania.Services.Auth.Register;
import com.example.dietmania.utils.GoTo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Register controller
 * Handles new user registration
 */
public class RegisterController {
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField kgField;
    
    @FXML
    private TextField lengthField;
    
    @FXML
    private TextField ageField;
    
    @FXML
    private ComboBox<String> genderCombo;
    
    @FXML
    private ComboBox<String> targetCombo;
    
    @FXML
    private void initialize() {

        genderCombo.getItems().addAll("MALE", "FEMALE");
        targetCombo.getItems().addAll("EARN WIGHT", "LOSE WIGHT" ,"FITT BODY");
    }
    
    @FXML
    private void handleRegister() {
        // Validate inputs
        if (nameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            passwordField.getText().isEmpty() ||
            kgField.getText().trim().isEmpty() ||
            lengthField.getText().trim().isEmpty() ||
            ageField.getText().trim().isEmpty() ||
            genderCombo.getValue() == null ||
            targetCombo.getValue() == null ) {
            
            showAlert("Error", "Please fill all fields");
            return;
        }
        
        try {
            User user = new User();
            user.setName(nameField.getText().trim());
            user.setEmail(emailField.getText().trim());
            user.setPassword(passwordField.getText());
            user.setKg(Integer.parseInt(kgField.getText().trim()));
            user.setLength(Integer.parseInt(lengthField.getText().trim()));
            user.setAge(Integer.parseInt(ageField.getText().trim()));
            user.setGender(genderCombo.getValue());
            user.setTarget(targetCombo.getValue());

            Register register  = new Register();

            try {
                if (register.register(user)) {
                    showAlert("Success", "Registration successful!");
                    navigateToMain();
                } else {
                    showAlert("Error", "Registration failed. Email may already exist.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers for weight, height, and age");
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        GoTo.page(
                (Node) event.getSource(),
                "login.fxml"
        );
    }
    
    private void navigateToMain() {
        try {
            Stage stage = (Stage) nameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

