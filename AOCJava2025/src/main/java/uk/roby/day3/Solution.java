package uk.roby.day3;

import uk.roby.Utility;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Solution {
    private final List<String> fileLines;

    public Solution(String filePath) {
        this.fileLines = Utility.readLines(filePath);
    }

    public AtomicInteger solvePart1() {
        AtomicInteger count = new AtomicInteger();

        fileLines.forEach( line -> {
            var firstDigit = -1;
            var secondDigit = -1;

            for (char ch : line.toCharArray()) {
                // TODO: assert ch >= '0' && ch <= '9' : "the char must be in [0-9]";
                var number = ch - '0'; // Clever way to convert a char into an int

                if (firstDigit == -1) {
                    firstDigit = number;
                } else if (secondDigit == -1) {
                    secondDigit = number;
                } else if (firstDigit < secondDigit) {
                    firstDigit = secondDigit;
                    secondDigit = number;
                } else if (secondDigit < number) {
                    secondDigit = number;
                }
            }

            count.addAndGet((firstDigit * 10) + secondDigit);
        });

        return count;
    }

    public AtomicLong solvePart2() {
        AtomicLong count = new AtomicLong();

        final var batteryLineLength = 12;
        fileLines.forEach( line -> {
            var currentIndex = -1;
            var digits = new Integer[batteryLineLength];

            for (int batteryPosition = 0; batteryPosition < batteryLineLength; batteryPosition++) {
                var closestPossibleIndex = currentIndex == -1 ? 0 : currentIndex + 1;
                var furthestPossibleIndex = line.length() - (batteryLineLength - batteryPosition) + 1;

                var greatestNumberInSubstring = findGreatestNumberIn(line.substring(closestPossibleIndex, furthestPossibleIndex));

                digits[batteryPosition] = greatestNumberInSubstring[0];
                currentIndex = closestPossibleIndex + greatestNumberInSubstring[1];
            }

            count.addAndGet(arrayToLong(digits));
        });

        return count;
    }

    private long arrayToLong(Integer[] numbers) {
        long result = 0;

        for (int number : numbers) { result = result * 10 + number; }

        return result;
    }

    // Looking for the biggest number in a substring which start from the last number found to the latest number that
    // can be checked while leaving space for the remaining batteries.
    // Example: for the first battery we will look from the first number until the 12th from the end
    private Integer[] findGreatestNumberIn(String substring) {
        var maxValue = -1;
        var valueIndex = -1;

        for (int i = 0; i < substring.length(); i++) {
            int digit = substring.charAt(i) - '0';
            if (digit > maxValue) {
                maxValue = digit;
                valueIndex = i;
            }

            // Exit early if 9 is met
            if (digit == 9) { break; }
        }

        return new Integer[]{maxValue, valueIndex};
    }

    static void main() {
        var solution = new uk.roby.day3.Solution("src/main/resources/day3/input.txt");

        IO.println("Part 1 solution: " + solution.solvePart1());
        IO.println("Part 2 solution: " + solution.solvePart2());
    }
}
