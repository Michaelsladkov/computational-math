package main.java.io;

import main.java.matrix.DoubleMatrix;

import java.util.Scanner;

import static main.java.io.Mode.FROM_CONSOLE;
import static main.java.io.Mode.FROM_FILE;

public class MatrixIO {
    private final Mode mode;
    private final Scanner scanner;

    public MatrixIO(Scanner scanner, Mode mode) {
        this.mode = mode;
        this.scanner = scanner;
    }

    public double[] readCoef(int size) throws FailedToReadMatrixException {
        putMessage("Enter free coefficients as one row, use spaces as separators");
        return readRowOfDoubles(size);
    }

    public DoubleMatrix readMatrix() throws FailedToReadMatrixException {
        final int rows = readNecessaryInteger("number of rows in coefficients matrix");
        if (rows <= 0) {
            throw new FailedToReadMatrixException();
        }
        double[][] matrix = new double[rows][rows];
        putMessage("Enter matrix row by row, use spaces to separate numbers in one row");
        for (int i = 0; i < rows; i++) {
            matrix[i] = readRowOfDoubles(rows);
        }
        return new DoubleMatrix(matrix);
    }

    private double[] readRowOfDoubles(int size) throws FailedToReadMatrixException {
        double[] ret = new double[size];
        boolean success = false;
        do {
            String[] elems;
            do {
                elems = scanner.nextLine().split(" +");
                if (elems.length != size) {
                    putMessage("Number of entered elements doesn't match entered columns number");
                    elems = null;
                }
            } while (elems == null && mode == FROM_CONSOLE);
            if (elems == null) {
                throw new FailedToReadMatrixException();
            }
            success = true;
            for (int j = 0; j < size; j++) {
                try {
                    ret[j] = Double.parseDouble(elems[j]);
                } catch (NumberFormatException e) {
                    numberFormatExceptionHandler(elems[j], "double");
                    putMessage("try again");
                    success = false;
                    break;
                }
            }
        } while (!success && mode == FROM_CONSOLE);
        return ret;
    }

    private int readNecessaryInteger(String name) {
        putMessage("Enter " + name + ":");
        int integer = -1;
        do {
            String str = scanner.nextLine();
            try {
                integer = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                numberFormatExceptionHandler(str, "integer");
                putMessage("try again");
                continue;
            }
            if (integer <= 0) {
                putMessage("This number should be positive");
                putMessage("try again");
                integer = -1;
            }
        } while (integer == -1 && mode == FROM_CONSOLE);
        return integer;
    }


    private void numberFormatExceptionHandler(String problem, String type) {
        System.out.println(problem + " is not correct format for " + type);
    }

    private void putMessage(String message) {
        if (mode == FROM_FILE) return;
        System.out.println(message);
    }
}
