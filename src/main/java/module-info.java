module com.example.ecomapp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.ecomapp to javafx.fxml;
    exports com.example.ecomapp;
}