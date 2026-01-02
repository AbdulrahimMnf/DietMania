package com.example.dietmania.Controllers;

import com.example.dietmania.Models.User;
import com.example.dietmania.Services.Resources.Users;
import com.example.dietmania.Services.Settings.SessionManager;
import com.example.dietmania.utils.GoTo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ProfileController {

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

    private  User authenticatedUser;

    @FXML
    private void initialize() {
        this.authenticatedUser = SessionManager.loadUser();


        fillDate(authenticatedUser);
        // Setup combos
        genderCombo.getItems().addAll("MALE", "FEMALE");
        targetCombo.getItems().addAll( "LOSE WIGHT" , "EARN WIGHT" , "STAY HEALTHY");
    }

    private void fillDate(User user) {
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        kgField.setText(String.valueOf(user.getKg()));
        lengthField.setText(String.valueOf(user.getLength()));
        ageField.setText(String.valueOf(user.getAge()));
        genderCombo.setValue(user.getGender());
        targetCombo.setValue(user.getTarget());
    }




    @FXML
    private void handleUpdate(ActionEvent event) throws SQLException {

        User model = new User();
        Users users = new Users();

        if (validateForm()) {
            model.setId(authenticatedUser.getId());
            model.setName(nameField.getText().trim());
            model.setEmail(emailField.getText().trim());
            model.setPassword(passwordField.getText());
            model.setKg(Integer.parseInt(kgField.getText().trim()));
            model.setLength(Integer.parseInt(lengthField.getText().trim()));
            model.setAge(Integer.parseInt(ageField.getText().trim()));
            model.setGender(genderCombo.getValue());
            model.setTarget(targetCombo.getValue());
            users.update(model);

            SessionManager.saveUser(model);
            GoTo.page((Node) event.getSource(), "main.fxml");

        }
    }

    @FXML
    private void handleDelete(ActionEvent event) throws SQLException {
        Users users = new Users();
        users.delete(authenticatedUser.getId());
        handleLogout(event);
    }

    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                passwordField.getText().isEmpty() ||
                kgField.getText().trim().isEmpty() ||
                lengthField.getText().trim().isEmpty() ||
                ageField.getText().trim().isEmpty() ||
                genderCombo.getValue() == null)
            return false;
        else {
            return true;
        }
    }



        @FXML

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
