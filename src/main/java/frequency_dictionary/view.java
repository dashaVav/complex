package frequency_dictionary;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;


import static jdk.jfr.consumer.EventStream.openFile;

public class view extends Application{

    @Override
    public void start(Stage stage) {

        TextField inputField = new TextField();
        inputField.setTranslateX(110);
        inputField.setTranslateY(20);
        Label textIn = new Label("Входящий файл");
        textIn.setTranslateX(10);


        TextField outputField = new TextField();
        outputField.setTranslateX(110);
        outputField.setTranslateY(20);
        Label textOut = new Label("Выходящий файл");
        textOut.setTranslateX(10);

        Button btn = new Button("Вычислить");
        btn.setTranslateX(110);
        btn.setTranslateY(20);

        btn.setOnAction(event -> {
            if(outputField.getText().isBlank()){
                outputField.setText((new model(inputField.getText()).read()));
            }
            else{
                outputField.setText((new model(inputField.getText(),outputField.getText()).read()));
            }

        });
        FlowPane root = new FlowPane(Orientation.VERTICAL, inputField, textIn,  outputField, textOut, btn);
        Scene scene = new Scene(root, 400, 180);

        stage.setScene(scene);

        stage.setTitle("frequency dictionary");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}