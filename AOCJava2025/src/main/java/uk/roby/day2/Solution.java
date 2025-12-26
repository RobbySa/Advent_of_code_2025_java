package uk.roby.day2;

import uk.roby.utility.FileUtility;
import uk.roby.utility.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public class Solution {
    private final List<String> rangeList;

    public Solution(String filePath) {
        this.rangeList = Arrays.stream(FileUtility.readLinesAsBlock(filePath).split(",")).toList();
    }

    public AtomicLong solvePart1() {
        AtomicLong count = new AtomicLong();
        var newRanges = getSubRanges();

        newRanges.forEach( newRange -> {
            var startOfRange = Long.parseLong(newRange.first());
            var endOfRange = Long.parseLong(newRange.second());

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

    public AtomicLong solvePart2() {
        AtomicLong count = new AtomicLong();
        Pattern pattern = Pattern.compile("(\\d+)\\1+$");

        rangeList.forEach( range -> {
            var splitRange = range.split("-");
            var startOfRange = Long.parseLong(splitRange[0]);
            var endOfRange = Long.parseLong(splitRange[1]);
            LongStream.rangeClosed(startOfRange, endOfRange).forEach( number -> {
                var stringNumber = Long.toString(number);

                if (pattern.matcher(stringNumber).matches()) { count.addAndGet(number); }
            });
        });

        return count;
    }

    private List<Pair<String>> getSubRanges() {
        List<Pair<String>> newRanges = new ArrayList<>();

        rangeList.forEach(range -> {
            var splitRange = range.split("-");

            // Account for different order of magnitude by splitting a single range into multiple smaller ranges
            var startOfRange = splitRange[0];
            var endOfRange = splitRange[1];

            // oom stands for order of magnitude
            for (int oom = startOfRange.length(); oom <= endOfRange.length(); oom++) {
                if (oom % 2 != 0) { continue; }
                var startOfNewRange = oom == startOfRange.length() ? startOfRange : "1" + "0".repeat(oom - 1);
                var endOfNewRange = oom < endOfRange.length() ? "9".repeat(oom) : endOfRange;

                newRanges.add(new Pair<>(startOfNewRange, endOfNewRange));
            }
        });

        return newRanges;
    }

    static void main() {
        var solution = new Solution("src/main/resources/day2/input.txt");

        IO.println("Part 1 solution: " + solution.solvePart1());
        IO.println("Part 2 solution: " + solution.solvePart2());
    }
}
