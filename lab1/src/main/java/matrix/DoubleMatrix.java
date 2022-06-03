package main.java.matrix;

import java.util.function.BinaryOperator;

public class DoubleMatrix {
    private final double[][] matrix;

    public DoubleMatrix(int rows, int columns, BinaryOperator<Double> generator) {
        matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = generator.apply((double) i, (double) j);
            }
        }
    }

    public DoubleMatrix(int rows, int columns) {
        matrix = new double[rows][columns];
    }

    public DoubleMatrix(double[][] array) {
        matrix = array;
    }

    public DoubleMatrix add(DoubleMatrix other) throws ImpossibleOperationException {
        if (getColumnsNumber() != other.getColumnsNumber() || getRowsNumber() != other.getRowsNumber()) {
            throw new ImpossibleOperationException("You can add matrix's only if they are same size");
        }
        double[][] ret = new double[getColumnsNumber()][getColumnsNumber()];
        for (int i = 0; i < getRowsNumber(); i++) {
            for (int j = 0; j < getColumnsNumber(); i++) {
                ret[i][j] = matrix[i][j] + other.elementAt(i, j);
            }
        }
        return new DoubleMatrix(ret);
    }

    public boolean isSquare() {
        return matrix.length == matrix[0].length;
    }

    public void swap(int i1, int i2) {
        double[] acc = matrix[i1];
        matrix[i1] = matrix[i2];
        matrix[i2] = acc;
    }

    public double determinant() throws ImpossibleOperationException {
        final double eps = 1e-11;
        if (!isSquare()) {
            throw new ImpossibleOperationException("Determinant for non-square matrix is undefined");
        }
        double[][] newArray = new double[matrix.length][];
        System.arraycopy(matrix, 0, newArray, 0, matrix.length);
        double det = 1;
        int n = getRowsNumber();
        for (int i = 0; i < n; i++) {
            int k = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(newArray[j][i]) > Math.abs(newArray[k][i])) k = j;
            }
            if (Math.abs(newArray[k][i]) < eps) {
                det = 0;
                break;
            }
            swap(i, k);
            if (i != k) {
                det *= -1;
            }
            det *= newArray[i][i];
            for (int j = i + 1; j < n; j++) newArray[i][j] /= newArray[i][i];
            for (int j = 0; j < n; j++) {
                if (j != i && Math.abs(newArray[j][i]) > eps) {
                    for (int c = i + 1; c < n; c++) {
                        newArray[j][c] -= newArray[i][c] * newArray[j][i];
                    }
                }
            }
        }
        return det;
    }

    public double[][] getDoubleArray() {
        return matrix;
    }

    public void addLineWithCoef(int target, int source, Number coef) {
        for (int j = 0; j < getColumnsNumber(); j++) {
            matrix[target][j] += matrix[source][j] * coef.doubleValue();
        }
    }

    public DoubleMatrix exclude(int row, int column) throws ImpossibleOperationException {
        if (getRowsNumber() < 2 || getColumnsNumber() < 2) {
            throw new ImpossibleOperationException("can not make matrix with size less than 1");
        }
        double[][] newMatrix = new double[getRowsNumber() - 1][getColumnsNumber() - 1];
        int subRow = 0;
        for (int i = 0; i < getRowsNumber(); i++) {
            if (i == row) {
                subRow = 1;
                continue;
            }
            int subColumn = 0;
            for (int j = 0; j < getColumnsNumber(); j++) {
                if (j == column) {
                    subColumn = 1;
                    continue;
                }
                newMatrix[i - subRow][j - subColumn] =matrix[i][j];
            }
        }
        return new DoubleMatrix(newMatrix);
    }

    public double elementAt(int i, int j) {
        return matrix[i][j];
    }

    public int getRowsNumber() {
        return matrix.length;
    }

    public int getColumnsNumber() {
        return matrix[0].length;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < getRowsNumber(); i++) {
            for (int j = 0; j< getColumnsNumber(); j++) {
                ret.append(String.format("%.4f", matrix[i][j]));
                ret.append("   ");
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    @Override
    protected DoubleMatrix clone() {
        double[][] newArray = new double[matrix.length][];
        System.arraycopy(matrix, 0, newArray, 0, matrix.length);
        return new DoubleMatrix(newArray);
    }
}
