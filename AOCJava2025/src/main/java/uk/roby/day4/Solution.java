package uk.roby.day4;

import uk.roby.Utility;

import java.util.List;

public class Solution  {
    private final List<List<String>> fileLinesSplit;

    public Solution(String filePath) {
        this.fileLinesSplit = Utility.splitLinesBy("", Utility.readLines(filePath));
    }

    public int solvePart1() {
        var count = 0;
        var numberOfLines = fileLinesSplit.size();
        var lineLength = fileLinesSplit.getFirst().size();

        for (int y = 0; y < numberOfLines; y++) {
            for (int x = 0; x < lineLength; x++) {
                var adjacentCells = Utility.getElementsAround(fileLinesSplit, new Integer[]{x, y});

                if (fileLinesSplit.get(y).get(x).equals("@")) {
                    if (countOccurrences("@", adjacentCells) < 4) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public int solvePart2() {
        var count = 0;

        var numberOfLines = fileLinesSplit.size();
        var lineLength = fileLinesSplit.getFirst().size();
        var removed = -1;

        while (removed != 0) {
            removed = 0;

            for (int y = 0; y < numberOfLines; y++) {
                for (int x = 0; x < lineLength; x++) {
                    var adjacentCells = Utility.getElementsAround(fileLinesSplit, new Integer[]{x, y});

                    if (fileLinesSplit.get(y).get(x).equals("@")) {
                        if (countOccurrences("@", adjacentCells) < 4) {
                            fileLinesSplit.get(y).set(x, ".");
                            removed++;
                        }
                    }
                }
            }

            count += removed;
        }

        return count;
    }

    public static int countOccurrences(String target, String[][] grid) {
        int count = 0;

        for (String[] strings : grid) {
            for (String string : strings) {
                if (string == null) { continue; }
                if (string.equals(target)) { count++; }
            }
        }

        return count;
    }

    static void main() {
        var solution = new uk.roby.day4.Solution("src/main/resources/day4/input.txt");

        IO.println("Part 1 solution: " + solution.solvePart1());
        IO.println("Part 2 solution: " + solution.solvePart2());
    }
}
