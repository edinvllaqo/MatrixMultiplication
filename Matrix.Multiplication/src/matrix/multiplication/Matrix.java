/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.multiplication;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author evllaço
 */
public final class Matrix {

    private final int rows;
    private final int columns;
    private final double[] array;

    public Matrix(int rows, int columns) {
        this.rows = checkRows(rows);
        this.columns = checkColumns(columns);
        this.array = new double[rows * columns];
    }

    public Matrix(Matrix other) {
        Objects.requireNonNull(other, "The other matrix is null.");
        this.rows = other.rows;
        this.columns = other.columns;
        this.array = other.array.clone();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double get(int x, int y) {
        return array[index(x, y)];
    }

    public void set(int x, int y, double value) {
        array[index(x, y)] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String separator = "";

        for (int y = 0; y < rows; ++y) {
            sb.append(separator);
            separator = "\n";

            for (int x = 0; x < columns; ++x) {
                sb.append(get(x, y)).append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!getClass().equals(o.getClass())) {
            return false;
        }

        Matrix other = (Matrix) o;

        if (getRows() != other.getRows()) {
            return false;
        }
        if (getColumns() != other.getColumns()) {
            return false;
        }
        return Arrays.equals(array, other.array);
    }

    private int index(int x, int y) {
        checkX(x);
        checkY(y);
        return y * columns + x;
    }

    private void checkX(int x) {
        if (x < 0) {
            throw new IndexOutOfBoundsException("x is negative " + x);
        }
        if (x >= columns) {
            throw new IndexOutOfBoundsException("x is too large " + x + ". The matrix has " + columns
                    + " columns.");
        }
    }

    private void checkY(int y) {
        if (y < 0) {
            throw new IndexOutOfBoundsException("y is negative " + y);
        }
        if (y >= columns) {
            throw new IndexOutOfBoundsException("x is too large " + y + ". The matrix has " + rows
                    + " rows.");
        }
    }

    private int checkRows(int rows) {
        return check(rows, "The number of rows is too small: " + rows);
    }

    private int checkColumns(int columns) {
        return check(columns, "The number of columns is too small: " + columns);
    }

    private int check(int number, String errorMessage) {
        if (number < 1) {
            throw new IllegalArgumentException(errorMessage);
        }
        return number;
    }

    /**
     * @param args the command line arguments
     */


}
