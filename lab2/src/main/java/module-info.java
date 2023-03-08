module lab2 {
    requires javafx.fxml;
    requires javafx.controls;

    opens frequency_dictionary to javafx.fxml;
    exports frequency_dictionary;


}