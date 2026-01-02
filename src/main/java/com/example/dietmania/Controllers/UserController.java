package com.example.dietmania.Controllers;


import com.example.dietmania.Models.User;
import com.example.dietmania.Services.Resources.Users;
import com.example.dietmania.Services.Settings.SessionManager;
import com.example.dietmania.utils.GoTo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;


public class UserController {
    
    @FXML
    private TableView<User> userTable;
    
    @FXML
    private TableColumn<User, String> nameColumn;
    
    @FXML
    private TableColumn<User, String> emailColumn;
    
    @FXML
    private TableColumn<User, String> roleColumn;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField passwordField;
    
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
    private ComboBox<String> roleCombo;


    private User selectedUser;
    
    @FXML
    private void initialize() {
        // Setup table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        
        // Setup combos
        genderCombo.getItems().addAll("MALE", "FEMALE");
        roleCombo.getItems().addAll( "ADMIN");
        targetCombo.getItems().addAll( "LOSE WIGHT" , "EARN WIGHT" , "STAY HEALTHY");

        // Load users
        loadUsers();
        
        // Setup table selection
        userTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedUser = newSelection;
                    fillForm(newSelection);
                }
            });
    }
    
    private void loadUsers() {
        Users users = new Users();
        try {
            userTable.getItems().setAll(users.list());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void fillForm(User user) {
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        kgField.setText(String.valueOf(user.getKg()));
        lengthField.setText(String.valueOf(user.getLength()));
        ageField.setText(String.valueOf(user.getAge()));
        genderCombo.setValue(user.getGender());
        targetCombo.setValue(user.getTarget());
        roleCombo.setValue(user.getRole());
    }
    
    @FXML
    private void handleAdd() {
        Users users = new Users();

        if (validateForm()) {
            User user = new User();
            user.setName(nameField.getText().trim());
            user.setEmail(emailField.getText().trim());
            user.setPassword(passwordField.getText());
            user.setKg(Integer.parseInt(kgField.getText().trim()));
            user.setLength(Integer.parseInt(lengthField.getText().trim()));
            user.setAge(Integer.parseInt(ageField.getText().trim()));
            user.setGender(genderCombo.getValue());
            user.setTarget(targetCombo.getValue().trim());
            user.setRole(roleCombo.getValue());

            try {
                if (users.add(user)) {
                    showAlert("Success", "User added successfully");
                    clearForm();
                    loadUsers();
                } else {
                    showAlert("Error", "Failed to add user");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @FXML
    private void handleUpdate() {

        Users users = new Users();

        if (selectedUser == null) {
            showAlert("Error", "Please select a user to update");
            return;
        }
        
        if (validateForm()) {
            selectedUser.setName(nameField.getText().trim());
            selectedUser.setEmail(emailField.getText().trim());
            selectedUser.setPassword(passwordField.getText());
            selectedUser.setKg(Integer.parseInt(kgField.getText().trim()));
            selectedUser.setLength(Integer.parseInt(lengthField.getText().trim()));
            selectedUser.setAge(Integer.parseInt(ageField.getText().trim()));
            selectedUser.setGender(genderCombo.getValue());
            selectedUser.setTarget(targetCombo.getValue());
            selectedUser.setRole(roleCombo.getValue());

            try {
                if (users.update(selectedUser)) {
                    showAlert("Success", "User updated successfully");
                    clearForm();
                    loadUsers();
                    selectedUser = null;
                } else {
                    showAlert("Error", "Failed to update user");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @FXML
    private void handleDelete() {
        Users users = new Users();


        if (selectedUser == null) {
            showAlert("Error", "Please select a user to delete");
            return;
        }

        try {
            if (users.delete(selectedUser.getId())) {
                showAlert("Success", "User deleted successfully");
                clearForm();
                loadUsers();
                selectedUser = null;
            } else {
                showAlert("Error", "Failed to delete user");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            passwordField.getText().isEmpty() ||
            kgField.getText().trim().isEmpty() ||
            lengthField.getText().trim().isEmpty() ||
            ageField.getText().trim().isEmpty() ||
            genderCombo.getValue() == null ||
            roleCombo.getValue() == null) {
            showAlert("Error", "Please fill all fields");
            return false;
        }
        
        try {
            Integer.parseInt(kgField.getText().trim());
            Integer.parseInt(lengthField.getText().trim());
            Integer.parseInt(ageField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers");
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        kgField.clear();
        lengthField.clear();
        ageField.clear();
        genderCombo.setValue(null);
        targetCombo.setValue(null);
        roleCombo.setValue(null);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void goBack(ActionEvent event) {
        GoTo.page(
                (Node) event.getSource(),
                "main.fxml"
        );
    }


    @FXML
    private void handleLogout(ActionEvent event) {
        SessionManager.clearSession();
        GoTo.page(
                (Node) event.getSource(),
                "login.fxml"
        );
    }


    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

