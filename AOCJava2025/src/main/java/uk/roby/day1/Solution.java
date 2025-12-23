package uk.roby.day1;

import uk.roby.SolutionUtility;

import java.util.List;

public class Solution extends SolutionUtility {
    private final List<String> fileLines;

    public Solution(String filePath) {
        this.fileLines = readLines(filePath);
    }

    public void solvePart1() {
        var pointer = 50;
        var count = 0;

        for (String line : this.fileLines) {
            if ('L' == line.charAt(0)) {
                pointer -= Integer.parseInt(line.substring(1));
            } else {
                pointer += Integer.parseInt(line.substring(1));
            }

            pointer %= 100;
            if (pointer == 0) { count++; }
        }

        IO.println(count);
    }

    public void solvePart2() {
        var pointer = 50;
        var count = 0;

        for (String line : this.fileLines) {
            var amount = Integer.parseInt(line.substring(1));
            var fullRotations = amount / 100;
            var partialRotation = amount % 100;

            // Subtract to represent a left rotation and add to represent a right rotation
            pointer = 'L' == line.charAt(0) ? (pointer - partialRotation) : (pointer + partialRotation);

            if (pointer >= 100) {
                pointer -= 100;
                count++;
            } else if (pointer < 0 && pointer != -partialRotation) {
                pointer += 100;
                count++;
            } else if (pointer < 0) {
                pointer += 100;
            } else if (pointer == 0) {
                count++;
            }

            count += fullRotations;
        }

        IO.println(count);
    }
}
