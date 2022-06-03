package main.java;

import main.java.gauss.Gauss;
import main.java.io.FailedToReadMatrixException;
import main.java.io.MatrixIO;
import main.java.io.Mode;
import main.java.matrix.DoubleMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static main.java.io.Mode.FROM_CONSOLE;
import static main.java.io.Mode.FROM_FILE;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, FailedToReadMatrixException {
        Scanner scanner;
        Mode mode;
        if (args.length == 0) {
            scanner = new Scanner(System.in);
            mode = FROM_CONSOLE;
        } else {
            String filename = args[0];
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("No such file");
                return;
            }
            if (!file.canRead()) {
                System.out.println("Can not read this file");
                return;
            }
            if (!file.isFile()) {
                System.out.println("This is not a file");
                return;
            }
            scanner = new Scanner(file);
            mode = FROM_FILE;
        }
        MatrixIO matrixIO = new MatrixIO(scanner, mode);
        DoubleMatrix matrix;
        try {
            matrix = matrixIO.readMatrix();
        } catch (FailedToReadMatrixException e) {
            System.out.println("Input doesn't contain valid matrix");
            return;
        }
        double[] freeCoefs = matrixIO.readCoef(matrix.getRowsNumber());
        DoubleMatrix extended = Gauss.getExtendedMatrix(matrix, freeCoefs);
        System.out.println("Extended matrix of system: ");
        System.out.println(extended);

        double determinant = matrix.determinant();
        System.out.println("Determinant of base system's matrix is: " + determinant);
        if (Math.abs(determinant) < 1e-11) {
            System.out.println("This system has no numeric solution");
            return;
        }

        long start = System.nanoTime();
        double[] solution = Gauss.getSolution(extended);
        long finish = System.nanoTime();
        System.out.println("Triangulated extended matrix of system: ");
        Gauss.triangulateMatrix(extended);
        System.out.println(extended);
        System.out.println("Unknowns: ");
        int i = 1;
        for (double d : solution) {
            System.out.printf("x%d = %.4f\n", i++, d);
        }
        System.out.println();
        double[] error = Gauss.getError(extended, solution);
        System.out.println("Errors: ");
        i = 1;
        for (double d : error) {
            System.out.println("d" + (i++) + " = " + d);
        }
        System.out.println();
        System.out.println("Time used: " + (finish - start) + " nanoseconds");
    }
}
