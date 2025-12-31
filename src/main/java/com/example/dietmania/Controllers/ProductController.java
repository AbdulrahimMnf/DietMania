package com.example.dietmania.Controllers;

import com.example.dietmania.Models.Category;
import com.example.dietmania.Models.Product;
import com.example.dietmania.Services.Resources.Categories;
import com.example.dietmania.Services.Resources.Products;
import com.example.dietmania.Services.Settings.SessionManager;
import com.example.dietmania.utils.GoTo;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ProductController {

    @FXML private TableView<Product> productTable;

    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, String> descColumn;
    @FXML private TableColumn<Product, Double> kcalColumn;
    @FXML private TableColumn<Product, Double> proteinColumn;
    @FXML private TableColumn<Product, Double> carbColumn;
    @FXML private TableColumn<Product, Double> fatColumn;
    @FXML private TableColumn<Product, String> categoryColumn;

    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private TextField kcalField;
    @FXML private TextField proteinField;
    @FXML private TextField carbField;
    @FXML private TextField fatField;

    @FXML private ComboBox<Category> categoryCombo;

    private Product selectedProduct;

    @FXML
    private void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        kcalColumn.setCellValueFactory(new PropertyValueFactory<>("kcal"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        carbColumn.setCellValueFactory(new PropertyValueFactory<>("carbohydrate"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("bodyOil"));

        categoryColumn.setCellValueFactory(cellData -> {
            Product p = cellData.getValue();
            return new SimpleStringProperty(
                    categoryCombo.getItems().stream()
                            .filter(c -> c.getId().equals(p.getCategory_id()))
                            .map(Category::getName)
                            .findFirst()
                            .orElse("")
            );
        });

        loadCategories();
        loadProducts();

        productTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        selectedProduct = newVal;
                        fillForm(newVal);
                    }
                }
        );
    }

    private void loadCategories() {
        Categories categories = new Categories();
        categoryCombo.getItems().clear();
        try {
            categoryCombo.getItems().addAll(categories.list());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProducts() {
        Products products = new Products();
        try {
            productTable.getItems().setAll(products.list());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillForm(Product p) {
        nameField.setText(p.getName());
        descriptionField.setText(p.getDescription());
        kcalField.setText(String.valueOf(p.getKcal()));
        proteinField.setText(String.valueOf(p.getProtein()));
        carbField.setText(String.valueOf(p.getCarbohydrate()));
        fatField.setText(String.valueOf(p.getBodyOil()));

        categoryCombo.getItems().stream()
                .filter(c -> c.getId().equals(p.getCategory_id()))
                .findFirst()
                .ifPresent(categoryCombo::setValue);
    }

    @FXML
    private void handleAdd() throws SQLException {
        Products products = new Products();

        Product p = new Product();
        p.setName(nameField.getText());
        p.setDescription(descriptionField.getText());
        p.setKcal(parse(kcalField));
        p.setProtein(parse(proteinField));
        p.setCarbohydrate(parse(carbField));
        p.setBodyOil(parse(fatField));
        p.setCategory_id(categoryCombo.getValue().getId());

        products.add(p);
        loadProducts();
        clearForm();
    }

    @FXML
    private void handleUpdate() throws SQLException {
        if (selectedProduct == null) return;

        Products products = new Products();
        selectedProduct.setName(nameField.getText());
        selectedProduct.setDescription(descriptionField.getText());
        selectedProduct.setKcal(parse(kcalField));
        selectedProduct.setProtein(parse(proteinField));
        selectedProduct.setCarbohydrate(parse(carbField));
        selectedProduct.setBodyOil(parse(fatField));
        selectedProduct.setCategory_id(categoryCombo.getValue().getId());

        products.update(selectedProduct);
        loadProducts();
        clearForm();
    }

    @FXML
    private void handleDelete() throws SQLException {
        if (selectedProduct == null) return;
        new Products().delete(selectedProduct.getId());
        loadProducts();
        clearForm();
    }

    private double parse(TextField tf) {
        return tf.getText().isEmpty() ? 0.0 : Double.parseDouble(tf.getText());
    }

    private void clearForm() {
        nameField.clear();
        descriptionField.clear();
        kcalField.clear();
        proteinField.clear();
        carbField.clear();
        fatField.clear();
        categoryCombo.setValue(null);
        selectedProduct = null;
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
