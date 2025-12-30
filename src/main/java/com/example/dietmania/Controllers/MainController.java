package com.example.dietmania.Controllers;

import com.example.dietmania.Models.User;
import com.example.dietmania.Services.Health.*;
import com.example.dietmania.Services.Settings.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {

    // User info labels
    @FXML private Label userNameLabel;
    @FXML private Label userEmailLabel;
    @FXML private Label userGenderLabel;
    @FXML private Label userWeightLabel;
    @FXML private Label userHeightLabel;
    @FXML private Label userTargetLabel;

    // Health metrics labels
    @FXML private Label bmrLabel;
    @FXML private Label bmiLabel;
    @FXML private Label proteinLabel;
    @FXML private Label waterLabel;
    @FXML private Label kcalLabel;

    @FXML
    public void initialize() {
        System.out.println("MainController initialized");

        try {
            // Load user from session
            User authenticatedUser = SessionManager.loadUser();

            // Set user information
            userNameLabel.setText(authenticatedUser.getName());
            userEmailLabel.setText(authenticatedUser.getEmail());
            userGenderLabel.setText(authenticatedUser.getGender());
            userWeightLabel.setText(authenticatedUser.getKg() + " kg");
            userHeightLabel.setText(authenticatedUser.getLength() + " cm");
            userTargetLabel.setText(authenticatedUser.getTarget());

            // Calculate and set health metrics
            bmrLabel.setText(String.valueOf(BMR.calculate(authenticatedUser)));
            bmiLabel.setText(String.format("%.1f", BMI.calculate(authenticatedUser)));
            proteinLabel.setText(DailyProteinNeeded.calculate(authenticatedUser) + " g");
            waterLabel.setText(DailyWaterNeeded.calculate(authenticatedUser) + " l");
            kcalLabel.setText(String.valueOf(DailyKcalNeeded.calculate(authenticatedUser)));

            System.out.println("All data loaded successfully");

        } catch (Exception e) {
            System.err.println("Error in initialize: " + e.getMessage());
            e.printStackTrace();

            // Set default values on error
            userNameLabel.setText("Error Loading");
            userEmailLabel.setText("Error Loading");
            userGenderLabel.setText("---");
            userWeightLabel.setText("---");
            userHeightLabel.setText("---");
            userTargetLabel.setText("---");
            bmrLabel.setText("---");
            bmiLabel.setText("---");
            proteinLabel.setText("---");
            waterLabel.setText("---");
            kcalLabel.setText("---");
        }
    }

    @FXML
    private void handleLogout() {
        System.out.println("Logout clicked");
        SessionManager.clearSession();
        // Close current window
        Stage stage = (Stage) userNameLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void close(ActionEvent event) {
        System.out.println("Close clicked");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void navigateToEditProfile(ActionEvent event) {
        System.out.println("Edit Profile clicked");
        // You'll need to implement navigation to edit profile page
    }
}