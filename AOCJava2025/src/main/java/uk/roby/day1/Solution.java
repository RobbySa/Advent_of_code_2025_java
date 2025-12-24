package uk.roby.day1;

import uk.roby.Utility;

import java.util.List;

public class Solution {
    private final List<String> fileLines;

    public Solution(String filePath) {
        this.fileLines = Utility.readLines(filePath);
    }

    public int solvePart1() {
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

        return count;
    }

    public int solvePart2() {
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

        return count;
    }

    static void main() {
        var solution = new Solution("src/main/resources/day1/input.txt");

        IO.println("Part 1 solution: " + solution.solvePart1());
        IO.println("Part 2 solution: " + solution.solvePart2());
    }
}
