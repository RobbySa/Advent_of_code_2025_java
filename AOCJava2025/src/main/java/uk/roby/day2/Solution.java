package uk.roby.day2;

import uk.roby.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class Solution {
    private final List<String> rangeList;

    public Solution(String filePath) {
        // TODO: Read the entire file as a block of text instead of flattening a list of size 1
        this.rangeList = Utility.splitLinesAndFlattenBy(",", Utility.readLines(filePath));
//        this.rangeList = readText(filePath).split(",");
    }

    public AtomicLong solvePart1() {
        AtomicLong count = new AtomicLong();
        var newRanges = getSubRanges(); // {[x, y], [x1, y2]...}

        // TODO: Make a Pair struct (using generics types)
        newRanges.forEach( newRange -> {
            var startOfRange = Long.parseLong(newRange[0]);
            var endOfRange = Long.parseLong(newRange[1]);

            LongStream.rangeClosed(startOfRange, endOfRange).forEach( number -> {
                var stringNumber = Long.toString(number);
                var halfLength = stringNumber.length() / 2;

                var firstHalf = stringNumber.substring(0, halfLength);
                var secondHalf = stringNumber.substring(halfLength);

                if (firstHalf.equals(secondHalf)) { count.addAndGet(number); }
            });
        });

        return count;
    }

    // TODO: do not create a regular expression by string (check if alternative is there)
    //   Pattern REPEATED_NUMBER = Pattern.compile("(\\d+)\\1+$")
    //   if (REPEATED_NUMBER.matcher(stringNumber).matches()) {...}
    public AtomicLong solvePart2() {
        AtomicLong count = new AtomicLong();

        rangeList.forEach( range -> {
            var splitRange = range.split("-");
            var startOfRange = Long.parseLong(splitRange[0]);
            var endOfRange = Long.parseLong(splitRange[1]);
            LongStream.rangeClosed(startOfRange, endOfRange).forEach( number -> {
                var stringNumber = Long.toString(number);

                if (stringNumber.matches("(\\d+)\\1+$")) { count.addAndGet(number); }
            });
        });

        return count;
    }

    private List<String[]> getSubRanges() {
        List<String[]> newRanges = new ArrayList<>();

        rangeList.forEach(range -> {
            var splitRange = range.split("-");

            // Account for different order of magnitude by splitting a single range into multiple smaller ranges
            var startOfRange = splitRange[0];
            var endOfRange = splitRange[1];

            // TODO: Change ofm to oom (order OF magnitude)
            // ofm stands for order of magnitude
            for (int ofm = startOfRange.length(); ofm <= endOfRange.length(); ofm++) {
                if (ofm % 2 != 0) { continue; }
                var startOfNewRange = ofm == startOfRange.length() ? startOfRange : "1" + "0".repeat(ofm - 1);
                var endOfNewRange = ofm < endOfRange.length() ? "9".repeat(ofm) : endOfRange;

                newRanges.add(new String[]{startOfNewRange, endOfNewRange});
            }
        });

        return newRanges;
    }

    static void main() {
        var solution = new uk.roby.day2.Solution("src/main/resources/day2/input.txt");

        IO.println("Part 1 solution: " + solution.solvePart1());
        IO.println("Part 2 solution: " + solution.solvePart2());
    }
}
