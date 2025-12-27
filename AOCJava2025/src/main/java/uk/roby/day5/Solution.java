package uk.roby.day5;

import uk.roby.utility.FileUtility;
import uk.roby.utility.Pair;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Solution {
    private final List<String> fileSplit;

    public Solution(String filePath) {
        this.fileSplit = Arrays.stream(FileUtility.readLinesAsBlock(filePath).split("\n\n")).toList();
    }

    public long solvePart1() {
        var fileRanges = fileSplit.getFirst().split("\n");
        var fileItems = fileSplit.get(1).split("\n");

        var ranges = Arrays.stream(fileRanges)
                .map(s -> new Pair<>(Long.parseLong(s.split("-")[0]), Long.parseLong(s.split("-")[1])))
                .toList();

        return Arrays.stream(fileItems)
                .filter(s -> {
                    for (Pair<Long> range : ranges) {
                        var number = Long.parseLong(s);

                        if (range.first() <= number && range.second() >= number) {
                            return true;
                        }
                    }

                    return false;
                })
                .count();
    }

    public long solvePart2() {
        AtomicLong count = new AtomicLong(0L);

        var fileRanges = fileSplit.getFirst().split("\n");
        var ranges = Arrays
                .stream(fileRanges)
                .map(range -> new Pair<>(Long.parseLong(range.split("-")[0]), Long.parseLong(range.split("-")[1])))
                .sorted(Comparator.comparing(Pair::first))
                .toList();

        AtomicReference<Pair<Long>> mergingElement = new AtomicReference<>();
        ranges.forEach(range -> {
            if (mergingElement.get() == null) {
                mergingElement.set(range);
            } else if (areRangesOverlapping(mergingElement.get(), range)) {
                mergingElement.set(getExpandedRange(mergingElement.get(), range));
            } else {
                count.addAndGet(getRangeNumbersCount(mergingElement.get()));
                mergingElement.set(range);
            }
        });
        count.addAndGet(getRangeNumbersCount(mergingElement.get()));

        return count.get();
    }

    private static Boolean areRangesOverlapping(Pair<Long> range1, Pair<Long> range2) {
        return range1.second() >= range2.first() && range1.first() <= range2.second();
    }

    private static Pair<Long> getExpandedRange(Pair<Long> range1, Pair<Long> range2) {
        var newLowestPoint = Math.min(range1.first(), range2.first());
        var newHighestPoint = Math.max(range1.second(), range2.second());

        return new Pair<>(newLowestPoint, newHighestPoint);
    }

    private static Long getRangeNumbersCount(Pair<Long> range) {
        return range.second() - range.first() + 1;
    }

    static void main() {
        var solution = new Solution("src/main/resources/day5/input.txt");

        IO.println("Part 1 solution: " + solution.solvePart1());
        IO.println("Part 2 solution: " + solution.solvePart2());
    }
}
