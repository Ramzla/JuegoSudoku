module com.example.juegosudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.juegosudoku to javafx.fxml;
    exports com.example.juegosudoku;
}