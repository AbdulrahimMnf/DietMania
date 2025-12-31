package com.example.dietmania.Controllers;


import com.example.dietmania.Models.Consumption;
import com.example.dietmania.Models.Product;
import com.example.dietmania.Models.User;
import com.example.dietmania.Services.Resources.Consumptions;
import com.example.dietmania.Services.Resources.Products;
import com.example.dietmania.Services.Settings.SessionManager;
import com.example.dietmania.utils.GoTo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Consumption controller
 * Handles user consumption management
 */
public class ConsumptionController {
    
    @FXML
    private TableView<Consumption> consumptionTable;
    
    @FXML
    private TableColumn<Consumption, String> dateColumn;
    
    @FXML
    private TableColumn<Consumption, Double> kcalColumn;
    
    @FXML
    private ListView<Product> productList;
    
    @FXML
    private ListView<Product> selectedProductsList;
    
    @FXML
    private Button addConsumptionButton;
    
    @FXML
    private Label totalKcalLabel;
    
    private ObservableList<Product> selectedProducts = FXCollections.observableArrayList();




    @FXML
    private void initialize() {
        // Setup table columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<>("totalKcal"));
        
        // Load products
        loadProducts();
        
        // Load consumptions
        loadConsumptions();
        
        // Setup selected products list
        selectedProductsList.setItems(selectedProducts);
        
        // Update total kcal when selection changes
        updateTotalKcal();
    }
    
    private void loadProducts() {
        Products products = new Products();
        try {
            productList.getItems().setAll(products.list());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void loadConsumptions() {
        Consumptions consumptions = new Consumptions();
        User currentUser = SessionManager.loadUser();
        if (currentUser != null) {
            try {
                consumptionTable.getItems().setAll(consumptions.listByUser(currentUser.getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @FXML
    private void handleAddProduct() {
        Product selected = productList.getSelectionModel().getSelectedItem();
        if (selected != null && !selectedProducts.contains(selected)) {
            selectedProducts.add(selected);
            updateTotalKcal();
        }
    }
    
    @FXML
    private void handleRemoveProduct() {
        Product selected = selectedProductsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedProducts.remove(selected);
            updateTotalKcal();
        }
    }
    
    @FXML
    private void handleAddConsumption() {




        Consumptions consumptions = new Consumptions();




        if (selectedProducts.isEmpty()) {
            showAlert("Error", "Please select at least one product");
            return;
        }
        
        User currentUser = SessionManager.loadUser();
        if (currentUser == null) {
            showAlert("Error", "User session not found");
            return;
        }
        
        Consumption consumption = new Consumption();
        consumption.setUser_id(currentUser.getId());
        
        // Calculate total kcal
        double totalKcal = selectedProducts.stream()
            .mapToDouble(Product::getKcal)
            .sum();
        consumption.setTotalKcal(totalKcal);
        
        // Convert products to list of IDs
        List<String> productIds = new ArrayList<>();
        for (Product p : selectedProducts) {
            productIds.add(p.getId());
        }
        consumption.setProducts(productIds);

        try {
            if (consumptions.add(consumption)) {
                showAlert("Success", "Consumption added successfully");
                selectedProducts.clear();
                updateTotalKcal();
                loadConsumptions();
            } else {
                showAlert("Error", "Failed to add consumption");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void updateTotalKcal() {
        double total = selectedProducts.stream()
            .mapToDouble(Product::getKcal)
            .sum();
        totalKcalLabel.setText(String.format("Total Kcal: %.2f", total));
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

