package uk.roby.day6;

import uk.roby.utility.FileUtility;
import uk.roby.utility.Grid;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Solution {
    private final List<String> fileLines;

    public Solution(String filePath) {
        this.fileLines = FileUtility.readLines(filePath);
    }

    public BigInteger solvePart1() {
        var workableLines = getLinesOfNumberFrom(this.fileLines);
        var operatorIndex = workableLines.length - 1;

        return IntStream.range(0, workableLines[0].length)
                        .mapToObj(column -> {
                            var operator = workableLines[operatorIndex][column];
                            return IntStream.range(0, operatorIndex)
                                            .mapToObj(row -> new BigInteger(workableLines[row][column]))
                                            .reduce(operator.equals("*") ? BigInteger.ONE : BigInteger.ZERO,
                                                    (a, b) -> operator.equals("*") ? a.multiply(b) : a.add(b));
                        }).reduce(BigInteger.ZERO, BigInteger::add);
    }

    // TODO: try to solve it by transposing it so that the numbers can be read more easily
    public BigInteger solvePart2() {
        var grid = new Grid(fileLines);
        var lastRow = grid.getNumberOfRows();
        var lastColumn = grid.getNumberOfColumns();

        var columnResults = new ArrayList<BigInteger>();
        var storedNumbers = new AtomicReference<>(new ArrayList<BigInteger>());
        var storedOperator = new AtomicReference<String>();

        IntStream.rangeClosed(0, lastColumn)
                .forEach(column -> {
                    if (column == lastColumn || grid.isColumnAll(" ", column)) {
                        columnResults.add(storedNumbers.get().stream()
                                .reduce(storedOperator.get().equals("*") ? BigInteger.ONE : BigInteger.ZERO,
                                        (a, b) -> storedOperator.get().equals("*") ? a.multiply(b) : a.add(b)));
                        // Reset accumulators
                        storedNumbers.set(new ArrayList<>());
                        storedOperator.set(null);
                    } else {
                        var lastElement = grid.getElementAt(lastRow - 1, column);
                        if (storedOperator.get() == null && !lastElement.isBlank()) { storedOperator.set(lastElement); }

                        var accumulator = new StringBuilder();

                        IntStream.range(0, lastRow - 1)
                            .forEach(row -> {
                                var element = grid.getElementAt(row, column);
                                if (!element.isBlank()) { accumulator.append(element); }
                            }
                        );

                        storedNumbers.get().add(new BigInteger(accumulator.toString()));
                    }
                });

        return columnResults.stream().reduce(BigInteger.ZERO, BigInteger::add);
    }

    private static String[][] getLinesOfNumberFrom(List<String> fileLines) {
        Pattern pattern = Pattern.compile("\\s+");
        return fileLines.stream()
                .map(line -> Arrays.stream(pattern.split(line))
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new))
                .toArray(String[][]::new);
    }

    static void main() {
        var solution = new Solution("src/main/resources/day6/input.txt");

        IO.println("Part 1 solution: " + solution.solvePart1());
        IO.println("Part 2 solution: " + solution.solvePart2());
    }
}
