package org.example;
import java.util.ArrayList;

/** класс работает как с комплексными, как и с действительными числами */

public class Matrix{

    /** количество столбцов в матрице */
    private final int column;

    /** количество строк в матрице */
    private final int row;

    /** матрица размером row х column */
    ArrayList<ArrayList<ComplexNumber>> matrix;

    /**
     * конструктор создающий матрицу row x column
     * @param row количество строк в матрице
     * @param column количество столбцов в матрице
     * @param matrix матрица
     */
    Matrix(int row, int column, ArrayList<ArrayList<ComplexNumber>> matrix) {
        this.column = column;
        this.row = row;
        this.matrix = matrix;
    }

    /**
     * @return матрику как строку
     */
    StringBuilder MatrixAsStr() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < row; i++){
            for (ComplexNumber n : matrix.get(i)) {
                str.append(n.algebraicForm()).append(" ");
            }
            str.append('\n');
        }
        return str;
    }

    /**
     * @param number комплексное число
     * @return матрицу умноженную на число
     */
    Matrix mulNumber(ComplexNumber number) {
        var mtrx = new ArrayList<ArrayList<ComplexNumber>> ();
        for (int i = 0; i < row; i++) {
            var mas = new ArrayList<ComplexNumber>();
            for (int j = 0; j < column; j ++){
                mas.add(matrix.get(i).get(j).mul(number));
            }
            mtrx.add(mas);
        }
        return new Matrix(row, column, mtrx);
    }

    /**
     * @param another матрица
     * @return сумму двух матриц
     */
    Matrix addMatrix(Matrix another) {
        if (row != another.row || column != another.column)
            throw new RuntimeException("Нельзя сложить матрицы, так как они разных размеров!");

        var mtrx = new ArrayList<ArrayList<ComplexNumber>> ();
        for (int i = 0; i < row; i++) {
            var mas = new ArrayList<ComplexNumber>();
            for (int j = 0; j < column; j ++){
                mas.add(matrix.get(i).get(j).add(another.matrix.get(i).get(j)));
            }
            mtrx.add(mas);
        }
        return new Matrix(row, column, mtrx);
    }

    /**
     * @param another матрица
     * @return разность двух матриц
     */
    Matrix decrMatrix(Matrix another) {
        if (row != another.row || column != another.column)
            throw new RuntimeException("Нельзя сложить, так как разные размеры");

        var mtrx = new ArrayList<ArrayList<ComplexNumber>> ();
        for (int i = 0; i < row; i++) {
            var mas = new ArrayList<ComplexNumber>();
            for (int j = 0; j < column; j ++){
                mas.add(matrix.get(i).get(j).decr(another.matrix.get(i).get(j)));
            }
            mtrx.add(mas);
        }
        return new Matrix(row, column, mtrx);
    }

    /**
     * @param another матрица
     * @return произведение двух матриц
     */
    Matrix mulMatrix(Matrix another) {
        if (column != another.row)
            throw new RuntimeException("Нельзя умножить, так как разные размеры");

        var mtrx = new ArrayList<ArrayList<ComplexNumber>> ();
        for (int i = 0; i < row; i++) {
            var mas = new ArrayList<ComplexNumber>();
            for (int j = 0; j < another.column; j ++){
                var n = new ComplexNumber();
                for (int s = 0; s < row; s++) {
                    n = n.add(matrix.get(i).get(s).mul(another.matrix.get(s).get(j)));
                }
                mas.add(n);
            }
            mtrx.add(mas);
        }
        return new Matrix(row, column, mtrx);
    }

    /**
     * @return транспонированная матрица
     */
    Matrix transposedMatrix() {
        var mtrx = new ArrayList<ArrayList<ComplexNumber>> ();
        for (int i = 0; i < column; i++) {
            var mas = new ArrayList<ComplexNumber>();
            for (int j = 0; j < row; j++) {
                mas.add(matrix.get(j).get(i));
            }
            mtrx.add(mas);
        }
        return new Matrix(column, row, mtrx);
    }

    /**
     * @return детерминант матрицы
     */
    ComplexNumber det() {
        if (column != row)
            throw new RuntimeException("Нельзя найти детерминант не квадратной матрицы");
        return determinant(matrix, row);
    }

    private ComplexNumber determinant(ArrayList<ArrayList<ComplexNumber>> mrtx, int n) {
        ComplexNumber number = new ComplexNumber();
        if (n == 2) {
            return (mrtx.get(0).get(0).mul(mrtx.get(1).get(1))).
                    decr(mrtx.get(1).get(0).mul(mrtx.get(0).get(1)));
        }
        for (int i = 0; i < n; i++) {
            var m = new ArrayList<ArrayList<ComplexNumber>> ();
            for (int a = 1; a < n; a++) {
                var m_ = new ArrayList<ComplexNumber>();
                for (int b = 0; b < n; b++) {
                    if (b != i) {
                        m_.add(mrtx.get(a).get(b));
                    }
                }
                if (!m_.isEmpty()) m.add(m_);
            }
            number = number.add(((mrtx.get(0).get(i))
                    .mul(new ComplexNumber(Math.pow(-1, 2 + i)))).mul(determinant(m, m.size())) );
        }
        return number;
    }

}
