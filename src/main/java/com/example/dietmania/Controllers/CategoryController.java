package com.example.dietmania.Controllers;


import com.example.dietmania.Models.Category;
import com.example.dietmania.Services.Resources.Categories;
import com.example.dietmania.Services.Settings.SessionManager;
import com.example.dietmania.utils.GoTo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.SQLException;


public class CategoryController {
    
    @FXML
    private TableView<Category> categoryTable;
    
    @FXML
    private TableColumn<Category, String> nameColumn;
    
    @FXML
    private TextField nameField;
    
    private Category selectedCategory;
    
    @FXML
    private void initialize() {
        // Setup table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        // Load categories
        loadCategories();
        
        // Setup table selection
        categoryTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedCategory = newSelection;
                    nameField.setText(newSelection.getName());
                }
            });
    }
    
    private void loadCategories() {

        Categories categories = new Categories();
        try {
            categoryTable.getItems().setAll(categories.list());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    private void handleAdd() {
        Categories categories = new Categories();

        if (nameField.getText().trim().isEmpty()) {
            showAlert("Error", "Please enter a category name");
            return;
        }
        
        Category category = new Category();
        category.setName(nameField.getText().trim());

        try {
            if (categories.add(category)) {
                showAlert("Success", "Category added successfully");
                nameField.clear();
                loadCategories();
            } else {
                showAlert("Error", "Failed to add category");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    private void handleUpdate() {
        Categories categories = new Categories();

        if (selectedCategory == null) {
            showAlert("Error", "Please select a category to update");
            return;
        }
        
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Error", "Please enter a category name");
            return;
        }
        
        selectedCategory.setName(nameField.getText().trim());

        try {
            if (categories.update(selectedCategory)) {
                showAlert("Success", "Category updated successfully");
                nameField.clear();
                loadCategories();
                selectedCategory = null;
            } else {
                showAlert("Error", "Failed to update category");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    private void handleDelete() {
        Categories categories = new Categories();

        if (selectedCategory == null) {
            showAlert("Error", "Please select a category to delete");
            return;
        }

        try {
            if (categories.delete(selectedCategory.getId())) {
                showAlert("Success", "Category deleted successfully");
                nameField.clear();
                loadCategories();
                selectedCategory = null;
            } else {
                showAlert("Error", "Failed to delete category");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

