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
public final class SequentialMatrixMultiplier implements MatrixMultiplier {

    @Override
    public Matrix multiply(Matrix left, Matrix right) {
        Objects.requireNonNull(left, "The left matrix is null.");
        Objects.requireNonNull(right, "The right matrix is null.");
        checkDimensions(left, right);

        Matrix result = new Matrix(left.getRows(), right.getColumns());

        for (int y = 0; y < left.getRows(); ++y) {
            for (int x = 0; x < right.getColumns(); ++x) {
                double sum = 0.0;

                for (int i = 0; i < left.getColumns(); ++i) {
                    sum += left.get(i, y) * right.get(x, i);
                }

                result.set(x, y, sum);
            }
        }

        return result;

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
