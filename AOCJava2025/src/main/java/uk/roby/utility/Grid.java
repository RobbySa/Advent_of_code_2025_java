package uk.roby.utility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Grid {
    private String[][] data;

    public Grid(List<String> lines) {
        this.data = toCharGrid(lines);
    }

    private String[][] toCharGrid(List<String> lines) {
        var grid = lines.stream()
                .map(s -> s.chars()
                        .mapToObj(c -> String.valueOf((char)c))
                        .toArray(String[]::new))
                .toArray(String[][]::new);

        // Add empty spaces to make sure that all the lines are the same length
        var maxLength = Arrays.stream(grid).mapToInt(row -> row.length).max().orElse(0);

        return Arrays.stream(grid)
                .map(row -> {
                    if (row.length < maxLength) {
                        String[] newRow = Arrays.copyOf(row, maxLength);
                        Arrays.fill(newRow, row.length, maxLength, " ");
                        return newRow;
                    } else {
                        return row;
                    }
                })
                .toArray(String[][]::new);
    }

    public void transpose() {
        if (this.data.length == 0) throw new IllegalArgumentException("Array is empty");

        int rows = getNumberOfRows();
        int columns = getNumberOfColumns();

        String[][] transposed = new String[columns][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                transposed[j][i] = this.data[i][j];
            }
        }

        this.data = transposed;
    }

    public String getElementAt(int row, int column) {
        return this.data[row][column];
    }

    public int getNumberOfRows() {
        return this.data.length;
    }

    public int getNumberOfColumns() {
        return this.data[0].length;
    }

    public Boolean isColumnAll(String character, int columnIndex) {
        var column = IntStream.range(0, getNumberOfRows()).mapToObj(row -> getElementAt(row, columnIndex)).toList();
        return column.stream().allMatch(element -> element.equals(character));
    }
}
