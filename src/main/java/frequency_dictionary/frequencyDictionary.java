package frequency_dictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class frequencyDictionary {

    public void open() {

    }

    public void close() {

    }

    public static void dictionary() {
        // словарь из всех букв
        Map<Character, Integer> map = new HashMap<>();
        IntStream.rangeClosed('A', 'Z').mapToObj(var -> (char) var).forEach(letter -> map.put(letter, 0));
        IntStream.rangeClosed('a', 'z').mapToObj(var -> (char) var).forEach(letter -> map.put(letter, 0));

        System.out.println("Введите путь к файлу:");
        String path = (new Scanner(System.in)).nextLine();
        String pathIn = new String(path).replace(new StringBuilder().append((char) 92), new StringBuilder().append((char) 92).append((char) 92));


        try {
            FileInputStream file = new FileInputStream(pathIn);
            int c;
            while ((c = file.read()) != -1) {
                if (map.containsKey((char) c))
                    map.put((char) c, map.get((char) c) + 1);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("err");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(map);


        String pathOut = (new StringBuilder(path)).insert(path.indexOf('.'), " - dictionary").toString();
        System.out.println(pathOut);

        try{
            File file = new File(pathOut);
        }
        catch (Exception e){
            System.out.println("err");
        }

        try {
            FileWriter writer = new FileWriter (pathOut);
            writer.write(map.toString());
            writer.close();
        }
        catch (Exception e){
            System.out.println("err");
        }

        pathOut.replace("\\\\", "\\");
        System.out.println(pathOut);

    }

    public static void main(String[] args){
        dictionary();
    }
}
