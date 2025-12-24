package uk.roby.day4;

import uk.roby.Utility;

import java.util.List;

public class Solution  {
    private final List<List<String>> fileLinesSplit;

    public Solution(String filePath) {
        this.fileLinesSplit = Utility.splitLinesBy("", Utility.readLines(filePath));
    }

    public void solvePart1() {
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

        IO.println(count);
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

    public void solvePart2() {
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

        IO.println(count);
    }
}
