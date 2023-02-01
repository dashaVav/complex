package org.example;

import java.util.Scanner;

public class ConsoleOutputInput {

    ConsoleOutputInput(){
        System.out.println("""
                Выбери с чем ты хочешь работать:
                1 - Комплексные числа
                2 - Матрицы""");
        int n = (new Scanner(System.in)).nextInt();
        if (n == 1) {
            ComplexNumber();
        }
        else if (n == 2) {
            Matrix();
        }
        else {
            System.out.print("Команда не найдена!");
        }
    }

    private String inputNumber(){
        System.out.println("Введи число вида a+bi/a/bi:");
        return (new Scanner(System.in)).nextLine();
    }

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
        switch (n) {
            case (1) -> System.out.println("Результат:\n" + new ComplexNumber(number.getReal()).algebraicForm());
            case (2) -> System.out.println("Результат:\n" + new ComplexNumber(number.getImag()).algebraicForm());
            case (3) -> System.out.println("Результат:\n" +
                    number.add(new ComplexNumber(this.inputNumber())).algebraicForm());
            case (4) -> System.out.println("Результат:\n" +
                    number.decr(new ComplexNumber(this.inputNumber())).algebraicForm());
            case (5) -> System.out.println("Результат:\n" +
                    number.mul(new ComplexNumber(this.inputNumber())).algebraicForm());
            case (6) -> System.out.println("Результат:\n" +
                    number.div(new ComplexNumber(this.inputNumber())).algebraicForm());
            case (7) -> System.out.println("Результат:\n" + number.trigonometricForm());
            case (8) -> System.out.println("Результат:\n" + number.algebraicForm());
            default -> System.out.println("Команда не найдена!");
        }
    }

    Matrix inputMatrix() {
        System.out.println("Введите количесто строк в матрице");
        var n = (new Scanner(System.in)).nextInt();

        System.out.println("Введите количесто столбцов в матрице");
        var m = (new Scanner(System.in)).nextInt();

        return new Matrix(n, m);
    }

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
        switch (n) {
            case (1) -> {
                System.out.println("Результат:");
                mtrx.printMatrix();
            }
            case (2) -> {
                System.out.println("Результат:");
                mtrx.mulNumber(new ComplexNumber(inputNumber())).printMatrix();
            }
            case (3) -> {
                System.out.println("Результат:");
                mtrx.addMatrix(inputMatrix()).printMatrix();
            }
            case (4) -> {
                System.out.println("Результат:");
                mtrx.decrMatrix(inputMatrix()).printMatrix();
            }
            case (5) -> {
                System.out.println("Результат:");
                mtrx.mulMatrix(inputMatrix()).printMatrix();
            }
            case (6) -> {
                System.out.println("Результат:");
                mtrx.transposedMatrix().printMatrix();
            }
            case (7) -> {
                System.out.println("Результат:");
                System.out.println(mtrx.det().algebraicForm());
            }
            default -> System.out.println("Команда не найдена!");
        }
    }


}
