package uk.roby.day2;

import uk.roby.SolutionUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.LongStream;

public class Solution extends SolutionUtility {
    private final List<String> rangeList;

    public Solution(String filePath) {
        this.rangeList = splitLinesAndFlattenBy(",", readLines(filePath));
    }

    public void solvePart1() {
        AtomicLong count = new AtomicLong();
        var newRanges = getSubRanges(); // {[x, y], [x1, y2]...}

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

        IO.println(count);
    }

    private List<String[]> getSubRanges() {
        List<String[]> newRanges = new ArrayList<>();

        rangeList.forEach(range -> {
            var splitRange = range.split("-");

            // Account for different order of magnitude by splitting a single range into multiple smaller ranges
            var startOfRange = splitRange[0];
            var endOfRange = splitRange[1];

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

    public void solvePart2() {
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

        IO.println(count);
    }
}
