package main.java;

import main.java.equations.Equation;
import main.java.equations.EquationSystem;
import main.java.equations.MyEquations;
import main.java.equations.MySystems;
import main.java.exceptions.NoLimitException;
import main.java.methods.SimpleIterationsMethod;
import main.java.methods.TangentsMethod;

import java.util.Scanner;

public class Main {
    private static final int NANOSECOND_IN_MILLISECOND = 1000000;
    public static void main(String[] args) throws NoLimitException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello. Select equation to solve:");
        for (Equation e : MyEquations.equations) {
            System.out.println((MyEquations.equations.indexOf(e) + 1) + ". " + e);
        }
        Integer equationIndex = Util.readIndex(scanner);
        Equation toSolve = MyEquations.equations.get(equationIndex - 1);
        Double persistence = Util.readDouble(scanner, "Enter persistence ", true);
        long start = System.nanoTime();
        double newtonRoot = TangentsMethod.solve(toSolve, persistence);
        System.out.println("Time required for Newton's method: " +
                (System.nanoTime() - start)/NANOSECOND_IN_MILLISECOND + "ms");
        System.out.println("Root by Newton's method: " + newtonRoot);
        System.out.println();

        Double iterationsInitial = Util.readDouble(scanner, "Initial root for iterations method", false);
        start = System.nanoTime();
        double iterationsRoot = SimpleIterationsMethod.solve(toSolve, iterationsInitial, persistence);
        System.out.println("Time required for iterations method: " +
                (System.nanoTime() - start)/NANOSECOND_IN_MILLISECOND + "ms");
        System.out.println("Root by iterations method: " + iterationsRoot);
        System.out.println("Absolute difference between methods: " + Math.abs(newtonRoot - iterationsRoot));
        System.out.println();

        System.out.println("Select system to solve: ");
        for (EquationSystem s : MySystems.equationSystems) {
            System.out.println((MySystems.equationSystems.indexOf(s) + 1) + ". ");
            System.out.println(s);
        }
        Integer systemIndex = Util.readIndex(scanner);
        Double systemPersistence = Util.readDouble(scanner, "Enter persistence for system: ", true);
        EquationSystem systemToSolve = MySystems.equationSystems.get(systemIndex - 1);
        try {
            double[] roots = SimpleIterationsMethod.solve(systemToSolve, systemPersistence, new double[]{1, 1, 1, 1});
            System.out.println("Roots for system: ");
            System.out.println("x=" + roots[0]);
            System.out.println("y=" + roots[1]);
            System.out.println("z=" + roots[2]);
            System.out.println("t=" + roots[3]);
        } catch (NoLimitException e) {
            System.out.println(e.getMessage());
        }
    }
}
