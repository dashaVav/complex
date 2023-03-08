package frequency_dictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class model {
    String pathIn;
    String pathOut;
    Map<Character, Integer> map = new HashMap<>();

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

    public String count(String pathIn, String pathOut){
        this.pathIn = pathIn;
        this.pathOut = pathOut;
        return read();
    }

}
