package form;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * графический интерфейс
 * запускает приложение
 */
public class View extends Application{
    @Override
    public void start(Stage stage) {
        TextField inputSurname = new TextField();
        inputSurname.setTranslateX(110);
        inputSurname.setTranslateY(20);
        Label textSurname = new Label("Фамилия");
        textSurname.setTranslateX(10);

        TextField inputName = new TextField();
        inputName.setTranslateX(110);
        inputName.setTranslateY(20);
        Label textName = new Label("Имя");
        textName.setTranslateX(10);

        TextField inputMiddleName = new TextField();
        inputMiddleName.setTranslateX(110);
        inputMiddleName.setTranslateY(20);
        Label textMiddleName = new Label("Отчество");
        textMiddleName.setTranslateX(10);

        TextField inputDate = new TextField();
        inputDate.setTranslateX(110);
        inputDate.setTranslateY(20);
        Label textDate = new Label("Дата рождения");
        textDate.setTranslateX(10);
        Label textDateNotice = new Label("Формат ввода: 01.01.1900");
        textDateNotice.setTranslateX(110);
        textDateNotice.setTranslateY(5);

        Button button = new Button("Вычислить");
        button.setTranslateX(110);
        button.setTranslateY(15);

        Label info = new Label();
        info.setTranslateX(120);
        info.setTranslateY(20);
        info.setScaleX(1.1);

        button.setOnAction(event -> {
            if (inputName.getText().isEmpty() || inputSurname.getText().isEmpty()
                    || inputMiddleName.getText().isEmpty() || inputDate.getText().isEmpty()) {
                info.setText("Нужно заполнить все поля");
            }
            else{
                Form form = new Form(inputSurname.getText(), inputName.getText(), inputMiddleName.getText(), inputDate.getText());
                info.setText("Данные:\n" + form.getFullName() + "\n" + form.getGender() + "\n" + form.getAge());
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, inputSurname, textSurname,
                inputName, textName,
                inputMiddleName, textMiddleName,
                inputDate, textDate,
                textDateNotice, button, info
        );
        Scene scene = new Scene(root, 450, 320);

        stage.setScene(scene);

        stage.setTitle("App");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
