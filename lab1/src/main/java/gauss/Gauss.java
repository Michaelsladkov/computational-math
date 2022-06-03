package main.java.gauss;

import main.java.matrix.DoubleMatrix;

public class Gauss {
    public static void triangulateMatrix(DoubleMatrix matrix) {
        for (int j = 0; j < matrix.getRowsNumber(); j++) {
            int k = j;
            for (int c = j; c < matrix.getRowsNumber(); c++) {
                if (Math.abs(matrix.elementAt(c, j)) > Math.abs(matrix.elementAt(k, j))) k = c;
            }
            if (k != j) matrix.swap(k, j);
            if (matrix.elementAt(j, j) == 0) continue;
            matrix.addLineWithCoef(j, j, 1/ matrix.elementAt(j, j) - 1);
            for (int i = j + 1; i < matrix.getRowsNumber(); i++) {
                matrix.addLineWithCoef(i, j, -matrix.elementAt(i, j));
            }
        }
    }

    public static DoubleMatrix getExtendedMatrix(DoubleMatrix matrix, double[] freeCoefs) {
        int rows = matrix.getRowsNumber();
        int columns = matrix.getColumnsNumber() + 1;
        double[][] newMatrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 1; j++) {
                newMatrix[i][j] = matrix.elementAt(i, j);
            }
            newMatrix[i][columns - 1] = freeCoefs[i];
        }
        return new DoubleMatrix(newMatrix);
    }

    public static double[] getSolution(DoubleMatrix extendedMatrix) {
        triangulateMatrix(extendedMatrix);
        double[] solution = new double[extendedMatrix.getRowsNumber()];
        for (int i = extendedMatrix.getRowsNumber() - 1; i >= 0; i--) {
            double substracted = 0;
            for (int j = extendedMatrix.getColumnsNumber() - 2; j > i; j--) {
                substracted += extendedMatrix.elementAt(i, j) * solution[j];
            }
            solution[i] = extendedMatrix.elementAt(i, extendedMatrix.getColumnsNumber() - 1) - substracted;
        }
        return solution;
    }

    public static double[] getError(DoubleMatrix extendedMatrix, double[] solution) {
        double[] ret = new double[solution.length];
        for (int i = 0; i < solution.length; i++) {
            double leftSum = 0;
            for (int j = 0; j < solution.length; j++) {
                leftSum += solution[j] * extendedMatrix.elementAt(i, j);
            }
            ret[i] = Math.abs(extendedMatrix.elementAt(i, solution.length) - leftSum);
        }
        return ret;
    }
}
