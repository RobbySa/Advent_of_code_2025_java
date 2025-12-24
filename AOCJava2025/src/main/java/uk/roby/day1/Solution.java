package uk.roby.day1;

import uk.roby.Utility;

import java.util.List;

public class Solution {
    private final List<String> fileLines;

    public Solution(String filePath) {
        this.fileLines = Utility.readLines(filePath);
    }

    public void solvePart1() {
        var dial = 50;
        var count = 0;

        for (String line : this.fileLines) {
            if ('L' == line.charAt(0)) {
                dial -= Integer.parseInt(line.substring(1));
            } else {
                dial += Integer.parseInt(line.substring(1));
            }

            dial %= 100;
            if (dial == 0) { count++; }
        }

        IO.println(count);
    }

    public void solvePart2() {
        var dial = 50;
        var count = 0;

        for (String line : this.fileLines) {
            var amount = Integer.parseInt(line.substring(1));
            var fullRotations = amount / 100;
            var partialRotation = amount % 100;

            // Subtract to represent a left rotation and add to represent a right rotation
            dial = 'L' == line.charAt(0) ? (dial - partialRotation) : (dial + partialRotation);

            if (dial >= 100) {
                dial -= 100;
                count++;
            } else if (dial < 0 && dial != -partialRotation) {
                dial += 100;
                count++;
            } else if (dial < 0) {
                dial += 100;
            } else if (dial == 0) {
                count++;
            }

            count += fullRotations;
        }

        IO.println(count);
    }
}
