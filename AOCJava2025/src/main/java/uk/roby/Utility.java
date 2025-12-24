package uk.roby;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Utility {
    public static Scanner getFileScanner(Path filePath) {
        try {
            InputStream inputStream = Files.newInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            return new Scanner(reader);
        } catch (IOException e) {
            IO.println("Something went wrong when reading the file: " + e.getMessage());

            return new Scanner("");
        }
    }

    public static List<String> readLines(String filePath) {
        Scanner scanner = getFileScanner(Path.of(filePath));
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!line.isEmpty()) { lines.add(line); }
        }

        scanner.close();

        return lines;
    }

    public static List<String> splitLinesAndFlattenBy(String delimiter, List<String> lines) {
        return lines.stream()
                    .flatMap(s -> Arrays.stream(s.split(delimiter)))
                    .collect(Collectors.toCollection(ArrayList::new));
    }

    // TECH-DEBT: Turn this and all its usage into List<ArrayList<String>>
    // TECH-DEBT: Account for potential lines = null -> return empty List
    public static List<List<String>> splitLinesBy(String delimiter, List<String> lines) {
        return lines.stream()
                    .map(s -> Arrays.stream(s.split(delimiter)).collect(Collectors.toCollection(ArrayList::new)))
                    .collect(Collectors.toCollection(ArrayList::new));
    }

    public static String[][] getElementsAround(List<List<String>> grid, Integer[] position) {
        var surroundings = new String[3][3];
        var x = position[0];
        var y = position[1];

        // Check line on top
        if (y == 0) {
            surroundings[0] = new String[]{null, null, null};
        } else {
            surroundings[0] = getElementsAroundIndex(x, grid.get(y - 1), false);
        }

        // Check line
        surroundings[1] = getElementsAroundIndex(x, grid.get(y), true);

        // Check line below
        if (y == grid.size() - 1) {
            surroundings[2] = new String[]{null, null, null};
        } else {
            surroundings[2] = getElementsAroundIndex(x, grid.get(y + 1), false);
        }

        return surroundings;
    }

    private static String[] getElementsAroundIndex(int index, List<String> line, Boolean excludeIndex) {
        var center = excludeIndex ? "Here" : line.get(index);

        if (index == 0) { return new String[]{null, center, line.get(index + 1)}; }
        if (index == line.size() - 1) { return new String[]{line.get(index - 1), center, null}; }
        return new String[]{line.get(index - 1), center, line.get(index + 1)};
    }
}
