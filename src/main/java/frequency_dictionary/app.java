package frequency_dictionary;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Label;

import java.util.Objects;

public class app extends Application{

    @Override
    public void start(Stage stage) {

        TextField inputField = new TextField();
        inputField.setTranslateX(94);
        inputField.setTranslateY(20);
        inputField.setPrefColumnCount(16);

        TextField outputField = new TextField();
        outputField.setTranslateX(94);
        outputField.setTranslateY(60);
        outputField.setPrefColumnCount(16);
        //TODO ИМЯ ВЫХОДНОГО ФАЙЛА
        outputField.setText("input.txt");

        Button btn = new Button("Вычислить");
        btn.setTranslateX(94);
        btn.setTranslateY(40);

        Label lbl = new Label();

//        btn.setOnAction(event -> lbl.setText("Input: " + inputField.getText()));
        FlowPane root = new FlowPane(Orientation.VERTICAL, inputField, btn, outputField);
        Scene scene = new Scene(root, 400, 180);

        stage.setScene(scene);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("py.png")));
        stage.setTitle("frequency dictionary");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}