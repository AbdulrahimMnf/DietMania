module com.example.dietmania {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dietmania to javafx.fxml;
    exports com.example.dietmania;
}