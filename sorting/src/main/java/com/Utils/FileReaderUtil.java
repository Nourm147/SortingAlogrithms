package com.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileReaderUtil {

    private FileReaderUtil() {
    }

    public static Map<String, int[]> readFiles(List<File> files) {
        Map<String, int[]> map = new LinkedHashMap<>();
        for (File file : files) {
            map.put(file.getName(), readFile(file.getPath()));
        }
        return map;
    }

    public static int[] readFile(String filePath) {
        List<Integer> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.useDelimiter("[,\\s]+"); // use comma and spaces as sperators
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }

        return list.stream().mapToInt(Integer::intValue).toArray();
    }

}
