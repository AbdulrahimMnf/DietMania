package com.example.dietmania.Controllers;

import com.example.dietmania.Models.Consumption;
import com.example.dietmania.Models.User;
import com.example.dietmania.Services.Health.*;
import com.example.dietmania.Services.Resources.Consumptions;
import com.example.dietmania.Services.Settings.SessionManager;
import com.example.dietmania.utils.GoTo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class MainController {

    // ADMIN panel
    @FXML private VBox adminSection;

    // USER INFO
    @FXML private Label userNameLabel;
    @FXML private Label userEmailLabel;
    @FXML private Label userGenderLabel;
    @FXML private Label userWeightLabel;
    @FXML private Label userHeightLabel;
    @FXML private Label userTargetLabel;

    // HEALTH  services calculates
    @FXML private Label bmrLabel;
    @FXML private Label bmiLabel;
    @FXML private Label proteinLabel;
    @FXML private Label waterLabel;
    @FXML private Label kcalLabel;

    // RECORDS  for CONSUMPTIONS
    @FXML private HBox recordsContainer;

    @FXML
    public void initialize() {

        try {
            User authenticatedUser = SessionManager.loadUser();

            // ADMIN VISIBILITY 
            boolean isAdmin = Objects.equals(authenticatedUser.getRole(), "ADMIN");
            adminSection.setVisible(isAdmin);
            adminSection.setManaged(isAdmin);

            // USER INFO 
            userNameLabel.setText(authenticatedUser.getName());
            userEmailLabel.setText(authenticatedUser.getEmail());
            userGenderLabel.setText(authenticatedUser.getGender());
            userWeightLabel.setText(authenticatedUser.getKg() + " kg");
            userHeightLabel.setText(authenticatedUser.getLength() + " cm");
            userTargetLabel.setText(authenticatedUser.getTarget());

            // HEALTH METRICS 
            bmrLabel.setText(String.valueOf(BMR.calculate(authenticatedUser)));
            bmiLabel.setText(String.format("%.1f", BMI.calculate(authenticatedUser)));
            proteinLabel.setText(DailyProteinNeeded.calculate(authenticatedUser) + " g");
            waterLabel.setText(DailyWaterNeeded.calculate(authenticatedUser) + " l");
            kcalLabel.setText(String.valueOf(DailyKcalNeeded.calculate(authenticatedUser)));

            
            
            
            
            
            // LOAD CONSUMPTIONS 
            loadConsumptions(authenticatedUser.getId());

            
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            setErrorState();
        }
    }

    //  CONSUMPTIONS load
    private void loadConsumptions(String userId) throws SQLException {

        recordsContainer.getChildren().clear();
        List<Consumption> consumptions = new Consumptions().listByUser(userId);

        for (int i = 0; i < consumptions.size(); i++) {
            VBox item = createRecordItem(consumptions.get(i));
            recordsContainer.getChildren().add(item);

            if (i < consumptions.size() - 1) {
                Rectangle separator = new Rectangle(1, 75);
                separator.setFill(Color.LIGHTGRAY);
                recordsContainer.getChildren().add(separator);
            }
        }
    }

    // custom date show to be ../../.... just
    private String formatDate(String createdAt) {
        try {
            LocalDateTime dateTime =
                    LocalDateTime.parse(createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return createdAt;
        }
    }


    private VBox createRecordItem(Consumption c) {

        String formattedDate = formatDate(c.getCreated_at());

        Label dateLabel = new Label(formattedDate);
        dateLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");
        dateLabel.setAlignment(Pos.CENTER);

        Label kcal = new Label(String.valueOf((int) c.getTotalKcal()) + " kcal");
        kcal.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #008e9a;");
        kcal.setAlignment(Pos.CENTER);

        VBox box = new VBox(10, dateLabel, kcal);
        box.setAlignment(Pos.CENTER);
        box.setMinWidth(120);

        return box;
    }

//--------------------------------------------------------------------------------------------------------------
























    @FXML
    private void handleLogout(ActionEvent event) {
        SessionManager.clearSession();
        GoTo.page((Node) event.getSource(), "login.fxml");
    }

    @FXML
    private void navigateToEditProfile(ActionEvent event) {
        System.out.println("Edit Profile clicked");
    }

    @FXML
    private void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void goToCategories(ActionEvent event) {
        GoTo.page((Node) event.getSource(), "categories.fxml");
    }

    @FXML
    public void goToProducts(ActionEvent event) {
        GoTo.page((Node) event.getSource(), "products.fxml");
    }

    @FXML
    public void goToUsers(ActionEvent event) {
        GoTo.page((Node) event.getSource(), "users.fxml");
    }

    @FXML
    public void goToConsumption(ActionEvent event) {
        GoTo.page((Node) event.getSource(), "consumptions.fxml");
    }

    // ERROR STATE
    private void setErrorState() {
        userNameLabel.setText("Error");
        userEmailLabel.setText("Error");
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
