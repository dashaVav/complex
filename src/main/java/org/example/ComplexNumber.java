package org.example;


/** класс работает как с комплексными, как и с действительными числами */
public class ComplexNumber {

    /** действительная часть числа */
    private double real;
    /** мнимая часть числа */
    private double imag;

    /** @return значение действительной части числа */
    public double getReal() {
        return real;
    }

    /** @return значение мнимой части числа */
    public double getImag() {
        return imag;
    }

    /** конструктор - создание нового объекта с нулевыми значениями real и imag */
    ComplexNumber () {
        real = 0;
        imag = 0;
    }

    /**
     * конструктор - создание нового объекта с заданными значениями real и imag
     * @param real действительная часть числа
     * @param imag мнимая часть числа
     */
    ComplexNumber (double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    /**
     * конструктор - создание нового объекта с заданным значением real
     * @param real действительная часть числа
     */
    ComplexNumber (double real) {
        this.real = real;
        this.imag = 0;
    }

    /**
     * конструктор - создание нового объекта с заданными значениями real и/или imag
     * @param str комплексное число вида a+bi/a/bi
     */
    ComplexNumber (String str) {
        real = 0;
        imag = 0;
        String[] mas = str.split("[-+]");
        int realIndex = 1;
        if (str.indexOf("-") == 0) {
            if (mas[1].contains("i")) {
                imag = - Double.parseDouble(mas[1].substring(0, mas[1].length() - 1));
                return;
            }
            real = - Double.parseDouble(mas[1]);
            realIndex++;
        }
        else if (str.indexOf("-") != 0) {
            if (mas[0].contains("i")) {
                imag = Double.parseDouble(mas[0].substring(0, mas[0].length() - 1));
                return;
            }
            real = Double.parseDouble(mas[0]);
        }
        if (str.indexOf("-", 1) != -1) {
            imag = - Double.parseDouble(mas[realIndex].substring(0, mas[realIndex].length() - 1));
        }
        if (str.contains("+")) {
            imag = Double.parseDouble(mas[realIndex].substring(0, mas[realIndex].length() - 1));
        }
    }

    /**
     * @param another комплексное число
     * @return результат сложения двух комплексных чисел
     */

    ComplexNumber add(ComplexNumber another){
        return new ComplexNumber(real + another.real,imag + another.imag);
    }

    /**
     * @param another комплексное число
     * @return разность двух комплексных чисел
     */
    ComplexNumber decr(ComplexNumber another){
        return new ComplexNumber(real - another.real,imag - another.imag);
    }

    /**
     * @param another комплексное число
     * @return прозведение двух комплексных чисел
     */
    ComplexNumber mul(ComplexNumber another){
        return new ComplexNumber(real * another.real - imag * another.imag,
                real * another.imag + imag * another.real);
    }

    /**
     * @param another комплексное число
     * @return частное двух комплексных чисел
     */
    ComplexNumber div(ComplexNumber another){
        if (another.real * another.real + another.imag * another.imag == 0)
            throw new RuntimeException("Нельзя делить на 0");

        return new ComplexNumber( (real * another.real + imag * another.imag)
                / (another.real * another.real + another.imag * another.imag),
                (imag * another.real - real * another.imag)
                        / (another.real * another.real + another.imag * another.imag));
    }

    /**
     * @return строку с тригонометрическим видом числа
     */
    String trigonometricForm() {
        double module = Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));
        double x = Math.atan(imag / real);
        if (real < 0 && imag >= 0) x += Math.PI;
        if (real < 0 && imag < 0) x -= Math.PI;
        if (real == 0 && imag >= 0) x = Math.PI / 2;
        if (real == 0 && imag < 0) x = - Math.PI / 2;
        x = Math.toDegrees(x);
        return String.format("%f * (cos(%f) + i * sin(%f))", module, x, x);
    }

    /**
     * @return строку с алгебраическим видом числа
     */
    String algebraicForm() {
        if (imag != 0 && real == 0) return String.format("%fi", imag);
        if (imag > 0) return String.format("%f+%fi", real, imag);
        if (imag == 0) return String.format("%f", real);
        return String.format("%f%fi", real, imag);
    }

}
