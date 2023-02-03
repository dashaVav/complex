package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleOutputInput {

    ConsoleOutputInput(){
        System.out.println("""
                Выбери с чем ты хочешь работать:
                1 - Комплексные числа
                2 - Матрицы""");
        int n = (new Scanner(System.in)).nextInt();
        if (n == 1) {
            this.ComplexNumber();
        }
        else if (n == 2) {
            this.Matrix();
        }
        else {
            System.out.print("Команда не найдена!");
        }
    }

    /**
     * @return введенное комплексное чилсо
     */
    private String inputNumber(){
        System.out.println("Введи число вида a+bi/a/bi:");
        return (new Scanner(System.in)).nextLine();
    }

    /**
     * консольный интерфейс для работы с комплексными числами
     */
    private void ComplexNumber() {
        var number = new ComplexNumber(this.inputNumber());
        System.out.print("""
            Операции с этим числом:
            1 - получить действительную часть числа
            2 - получить мнимую часть числа
            3 - найти сумму двух чисел
            4 - найти разность чисел
            5 - найти произведение чисел
            6 - найти частное чисел
            7 - вывести в тригонометрическом виде
            8 - вывести в алгебраическом виде
            """);
        int n = (new Scanner(System.in)).nextInt();
        System.out.println("Результат:");
        switch (n) {
            case (1) -> System.out.println(new ComplexNumber(number.getReal()).algebraicForm());
            case (2) -> System.out.println(new ComplexNumber(number.getImag()).algebraicForm());
            case (3) -> System.out.println(number.add(new ComplexNumber(this.inputNumber())).algebraicForm());
            case (4) -> System.out.println(number.decr(new ComplexNumber(this.inputNumber())).algebraicForm());
            case (5) -> System.out.println(number.mul(new ComplexNumber(this.inputNumber())).algebraicForm());
            case (6) -> System.out.println(number.div(new ComplexNumber(this.inputNumber())).algebraicForm());
            case (7) -> System.out.println(number.trigonometricForm());
            case (8) -> System.out.println(number.algebraicForm());
            default -> System.out.println("Команда не найдена!");
        }
    }

    /**
     * ввод матрицы вида n х m
     * х11 х12
     * х21 х22
     * @return введенную матрицу
     */
    private Matrix inputMatrix() {
        System.out.println("Введите количесто строк в матрице");
        var n = (new Scanner(System.in)).nextInt();

        System.out.println("Введите количесто столбцов в матрице");
        var m = (new Scanner(System.in)).nextInt();

        System.out.println("Введите матрицу:");
            var mtrx = new ArrayList<ArrayList<ComplexNumber>> ();
            for (int i = 0; i < n; i++) {
                var mas = new ArrayList<ComplexNumber>();
                String[] numbers = new Scanner(System.in).nextLine().split(" ");
                for (int j = 0; j < n; j++) {
                    mas.add(new ComplexNumber(numbers[j]));
                }
                mtrx.add(mas);
            }
        return new Matrix(n, m, mtrx);
    }

    /**
     * консольный интерфейс для работы с матрицами
     */
    private void Matrix() {
        var mtrx = inputMatrix();
        System.out.print("""
            Операции с этой матрицей:
            1 - вывести матрицу
            2 - умножить матрицу на число
            3 - сложить с другой матрицей
            4 - вычесть другую матрицу
            5 - умножить на другую матрицу
            6 - транспонированная матрица
            7 - найти детрминант
            """);
        int n = (new Scanner(System.in)).nextInt();
        System.out.println("Результат:");
        switch (n) {
            case (1) -> System.out.println(mtrx.MatrixAsStr());
            case (2) -> System.out.println(mtrx.mulNumber(new ComplexNumber(inputNumber())).MatrixAsStr());
            case (3) -> System.out.println(mtrx.addMatrix(inputMatrix()).MatrixAsStr());
            case (4) -> System.out.println(mtrx.decrMatrix(inputMatrix()).MatrixAsStr());
            case (5) -> System.out.println(mtrx.mulMatrix(inputMatrix()).MatrixAsStr());
            case (6) -> System.out.println(mtrx.transposedMatrix().MatrixAsStr());
            case (7) -> System.out.println(mtrx.det().algebraicForm());
            default -> System.out.println("Команда не найдена!");
        }
    }

}
