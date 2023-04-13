module lab3 {
    requires javafx.fxml;
    requires javafx.controls;

    opens form to javafx.fxml;
    exports form;

}