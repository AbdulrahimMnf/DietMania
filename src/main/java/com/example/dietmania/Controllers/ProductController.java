package com.example.dietmania.Controllers;


import com.example.dietmania.Models.Category;
import com.example.dietmania.Models.Product;
import com.example.dietmania.Services.Resources.Categories;
import com.example.dietmania.Services.Resources.Products;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

/**
 * Product controller
 * Handles product management (Admin only)
 */
public class ProductController {
    
    @FXML
    private TableView<Product> productTable;
    
    @FXML
    private TableColumn<Product, String> nameColumn;
    
    @FXML
    private TableColumn<Product, Double> kcalColumn;
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField descriptionField;
    
    @FXML
    private TextField kcalField;
    
    @FXML
    private TextField proteinField;
    
    @FXML
    private TextField carbField;
    
    @FXML
    private TextField fatField;
    
    @FXML
    private ComboBox<String> categoryCombo;
    
    private Product selectedProduct;
    
    @FXML
    private void initialize() {
        // Setup table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<>("kcal"));

        Categories  categories = new Categories();
        // Load categories
        categoryCombo.getItems().clear();
        try {
            for (Category cat : categories.list()) {
                categoryCombo.getItems().add(cat.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Load products
        loadProducts();
        
        // Setup table selection
        productTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedProduct = newSelection;
                    fillForm(newSelection);
                }
            });
    }
    
    private void loadProducts() {
        Products products  = new Products();
        try {
            productTable.getItems().setAll(products.list());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void fillForm(Product product) {
        nameField.setText(product.getName());
        descriptionField.setText(product.getDescription());
        kcalField.setText(String.valueOf(product.getKcal()));
        proteinField.setText(String.valueOf(product.getProtein()));
        carbField.setText(String.valueOf(product.getCarbohydrate()));
        fatField.setText(String.valueOf(product.getBodyOil()));
        categoryCombo.setValue(product.getCategory_id());
    }
    
    @FXML
    private void handleAdd() {


        Products products  = new Products();

        if (validateForm()) {
            Product product = new Product();
            product.setName(nameField.getText().trim());
            product.setDescription(descriptionField.getText().trim());
            product.setKcal(Double.parseDouble(kcalField.getText().trim()));
            product.setProtein(Double.parseDouble(proteinField.getText().trim()));
            product.setCarbohydrate(Double.parseDouble(carbField.getText().trim()));
            product.setBodyOil(Double.parseDouble(fatField.getText().trim()));
            product.setCategory_id(categoryCombo.getValue());

            try {
                if (products.add(product)) {
                    showAlert("Success", "Product added successfully");
                    clearForm();
                    loadProducts();
                } else {
                    showAlert("Error", "Failed to add product");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @FXML
    private void handleUpdate() {

        Products products  = new Products();

        if (selectedProduct == null) {
            showAlert("Error", "Please select a product to update");
            return;
        }
        
        if (validateForm()) {
            selectedProduct.setName(nameField.getText().trim());
            selectedProduct.setDescription(descriptionField.getText().trim());
            selectedProduct.setKcal(Double.parseDouble(kcalField.getText().trim()));
            selectedProduct.setProtein(Double.parseDouble(proteinField.getText().trim()));
            selectedProduct.setCarbohydrate(Double.parseDouble(carbField.getText().trim()));
            selectedProduct.setBodyOil(Double.parseDouble(fatField.getText().trim()));
            selectedProduct.setCategory_id(categoryCombo.getValue());

            try {
                if (products.update(selectedProduct)) {
                    showAlert("Success", "Product updated successfully");
                    clearForm();
                    loadProducts();
                    selectedProduct = null;
                } else {
                    showAlert("Error", "Failed to update product");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @FXML
    private void handleDelete() {
        Products products  = new Products();

        if (selectedProduct == null) {
            showAlert("Error", "Please select a product to delete");
            return;
        }

        try {
            if (products.delete(selectedProduct.getId())) {
                showAlert("Success", "Product deleted successfully");
                clearForm();
                loadProducts();
                selectedProduct = null;
            } else {
                showAlert("Error", "Failed to delete product");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty() ||
            kcalField.getText().trim().isEmpty() ||
            categoryCombo.getValue() == null) {
            showAlert("Error", "Please fill required fields");
            return false;
        }
        
        try {
            Double.parseDouble(kcalField.getText().trim());
            if (!proteinField.getText().trim().isEmpty()) {
                Double.parseDouble(proteinField.getText().trim());
            }
            if (!carbField.getText().trim().isEmpty()) {
                Double.parseDouble(carbField.getText().trim());
            }
            if (!fatField.getText().trim().isEmpty()) {
                Double.parseDouble(fatField.getText().trim());
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numbers");
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        nameField.clear();
        descriptionField.clear();
        kcalField.clear();
        proteinField.clear();
        carbField.clear();
        fatField.clear();
        categoryCombo.setValue(null);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

