package form;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Form {
    private final String surname;
    private final String name;
    private final String middleName;
    private final String birthDate;

    /**
     * конструктор
     * @param surname фамилия
     * @param name имя
     * @param middleName отчество
     * @param birthDate дата рождения в формате "01.01.1900"
     */
    Form(String surname, String name, String middleName, String birthDate){
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.birthDate = birthDate;
    }

    /**
     * @return строку с указанием пола человека
     */
    public String getGender(){
        if (middleName.endsWith("ич")  || middleName.endsWith("оглы")) return "мужчина";
        if (middleName.endsWith("на") || middleName.endsWith("кызы")) return "женщина";
        return "Невозможно определить пол (ФИО указано не верно)";
    }

    /**
     * @return строку с фамилией и инициалами в формате "Иванов И.И."
     */
    public String getFullName(){
        return surname + " " + name.charAt(0) + ". " + middleName.charAt(0) + ".";
    }

    /**
     * @return результат проверки - верно ли указана дата для високосного года
     */
    private boolean leapYear(){
        String[] date = birthDate.split("\\.");
        int year = Integer.parseInt(date[2]);
        return Integer.parseInt(date[0]) != 29 || Integer.parseInt(date[1]) != 2 || year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    /**
     * @return строку с возрастом человека в формате "22 года"
     */

    public String getAge(){
        try {
            if (!leapYear())  return "Дата рождения указана не верно";
            LocalDate birthDate_ = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            int age = Period.between(birthDate_, LocalDate.now()).getYears();
            if (Period.between(birthDate_, LocalDate.now()).getDays() < 0) return "Дата рождения указана не верно";

            String ageStr = age + " ";
            int year = age % 10;
            if (year == 1 && age != 11) {
                ageStr += "год";
            }
            else if (year > 1 && year < 5 && (age < 10 || age > 20)) {
                ageStr += "года";
            }
            else {
                ageStr += "лет";
            }
            return ageStr;
        }
        catch (Exception e){
            return "Дата рождения указана не верно";
        }
    }
}