/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.multiplication.support;

import java.util.Objects;
import matrix.multiplication.Matrix;
import matrix.multiplication.MatrixMultiplier;

/**
 *
 * @author evllaço
 */
public final class ParallelMatrixMultiplier implements MatrixMultiplier {

    private static final int MINIMUM_WORKLOAD = 10_000;

    @Override
    public Matrix multiply(Matrix left, Matrix right) {
        Objects.requireNonNull(left, "The left matrix is null.");
        Objects.requireNonNull(right, "The right matrix is null.");
        checkDimensions(left, right);

        int workload = left.getRows() + right.getColumns() + right.getRows();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        numberOfThreads = Math.min(numberOfThreads, workload / MINIMUM_WORKLOAD);
        numberOfThreads = Math.min(numberOfThreads, left.getRows());
        numberOfThreads = Math.max(numberOfThreads, 1);

        if (numberOfThreads == 1) {
            return new SequentialMatrixMultiplier().multiply(left, right);
        }

        Matrix result = new Matrix(left.getRows(), right.getColumns());
        MultiplierThread[] threads = new MultiplierThread[numberOfThreads - 1];
        int basicRowsPerThreads = left.getRows() / numberOfThreads;
        int startRow = 0;

        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new MultiplierThread(left,
                    right,
                    result,
                    startRow, basicRowsPerThreads);
            threads[i].start();
            startRow += basicRowsPerThreads;
        }

        new MultiplierThread(left,
                right,
                result,
                startRow,
                basicRowsPerThreads + left.getRows() % basicRowsPerThreads)
                .run();

        for (MultiplierThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException("A thread interrupted", ex);
            }
        }

        return result;
    }

    private static final class MultiplierThread extends Thread {

        private final Matrix left;
        private final Matrix right;
        private final Matrix result;
        private final int startRow;
        private final int rows;

        public MultiplierThread(Matrix left,
                Matrix right,
                Matrix result,
                int startRow,
                int rows) {
            this.left = left;
            this.right = right;
            this.result = result;
            this.startRow = startRow;
            this.rows = rows;
        }

        @Override
        public void run() {
            for (int y = startRow; y < startRow + rows; ++y) {
                for (int x = 0; x < right.getColumns(); ++x) {
                    double sum = 0.0;

                    for (int i = 0; i < left.getColumns(); ++i) {
                        sum += left.get(i, y) * right.get(x, i);
                    }
                    result.set(x, y, sum);
                }
            }
        }
    }

    private void checkDimensions(Matrix left, Matrix right) {
        if (left.getColumns() != right.getRows()) {
            throw new IllegalArgumentException(
                    "Trying to multiply non-compatibile matrices. Columns of "
                    + "left matrix: " + left.getColumns() + ". Rows of "
                    + "right matrix: " + right.getRows());
        }
    }

}
