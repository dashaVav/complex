package frequency_dictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class model {

    String pathIn;
    String pathOut;
    Map<Character, Integer> map;

    model(String pathIn){
        this.pathIn = pathIn;
    }

    model(String pathIn, String pathOut) {
        this.pathIn = pathIn;
        this.pathOut = pathOut;
    }

    public String read() {
        map = new HashMap<>();
        IntStream.rangeClosed('A', 'Z').mapToObj(var -> (char) var).forEach(letter -> map.put(letter, 0));
        IntStream.rangeClosed('a', 'z').mapToObj(var -> (char) var).forEach(letter -> map.put(letter, 0));

        try(BufferedReader reader = new BufferedReader(new FileReader(pathIn))) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                if (map.containsKey((char) symbol))
                    map.put((char) symbol, map.get((char) symbol) + 1);
            }
            pathOut = (new StringBuilder(pathIn)).insert(pathIn.indexOf('.'), " - dictionary").toString();
            return write();
        } catch (FileNotFoundException ex) {
            System.out.println("Ошибка при открытии файла");
            return "Ошибка при открытии файла";
        }
        catch (IOException e) {
            System.out.println("Ошибка");
            return "Ошибка";
        }
    }

    private String write() {
        try (FileWriter writer = new FileWriter (pathOut)){
            writer.write(map.toString().replace(", ", "\n")
                    .replace("{", "")
                    .replace("}", ""));
            return pathOut;
        }
        catch (FileNotFoundException ex) {
            System.out.println("Ошибка при открытии файла");
            return "Ошибка при открытии файла";
        }
            catch (IOException e) {
            System.out.println("Ошибка");
            return "Ошибка";
        }
    }

}
