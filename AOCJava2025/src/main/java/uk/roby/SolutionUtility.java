package uk.roby;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class SolutionUtility {
    public Scanner getFileScanner(Path filePath) {
        try {
            InputStream inputStream = Files.newInputStream(filePath);
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

            return new Scanner(reader);
        } catch (IOException e) {
            IO.println("Something went wrong when reading the file: " + e.getMessage());

            return new Scanner("");
        }
    }

    public List<String> readLines(String filePath) {
        Scanner scanner = getFileScanner(Path.of(filePath));
        List<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!line.isEmpty()) { lines.add(line); }
        }

        scanner.close();

        return lines;
    }
}
