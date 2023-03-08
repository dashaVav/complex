package frequency_dictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * частотный словарь
 */
public class model {
    /**
     * входной файл
     */
    String pathIn;
    /**
     * выходной файл
     */
    String pathOut;
    /**
     * частотный словарь
     */
    Map<Character, Integer> map = new HashMap<>();

    /**
     * открывает файл для чтения
     * считает кол-во употреблений в нем больших и малых букв английского алфавита, записывает в map
     * вызывает функцию для записи результата в файл
     * @return строку со статусом выполнения операции
     */
    private String read() {
        IntStream.rangeClosed('A', 'Z').mapToObj(var -> (char) var).forEach(letter -> map.put(letter, 0));
        IntStream.rangeClosed('a', 'z').mapToObj(var -> (char) var).forEach(letter -> map.put(letter, 0));

        try(BufferedReader reader = new BufferedReader(new FileReader(pathIn))) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                if (map.containsKey((char) symbol))
                    map.put((char) symbol, map.get((char) symbol) + 1);
            }
            return write();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return "Ошибка при открытии файла на чтение";
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return "Ошибка";
        }
    }

    /**
     * записывает результат в файл
     * @return строку со статусом выполнения операции
     */
    private String write() {
        try (FileWriter writer = new FileWriter (pathOut)){
            writer.write(map.toString().replace(", ", "\n")
                    .replace("{", "")
                    .replace("}", ""));
            return "Готово!";
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return "Ошибка при открытии файла на запись";
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            return "Ошибка";
        }
    }

    /**
     * инициализирует входной и выходной файл
     * вызывает функцию для чтения из файла
     * @param pathIn  входной файл
     * @param pathOut выходной файл
     * @return строку со статусом выполнения операции
     */
    public String count(String pathIn, String pathOut){
        this.pathIn = pathIn;
        this.pathOut = pathOut;
        return read();
    }

}
