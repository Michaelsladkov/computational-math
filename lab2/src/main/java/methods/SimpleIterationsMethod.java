package main.java.methods;

import main.java.equations.Equation;
import main.java.equations.EquationSystem;
import main.java.exceptions.NoLimitException;

import java.util.Random;
import java.util.function.UnaryOperator;

public class SimpleIterationsMethod {
    private static final int REASONABLE_CNT = 500;
    private static final int CRITICAL_CNT = 5000000;
    private static final int EQUATIONS_NUMBER = 4;
    public static double solve(Equation equation, double initialX, double persistence)
            throws NoLimitException {
        double x = initialX;
        int cnt = 0;
        while (Math.abs(equation.calculate(x)) > persistence / 10) {
            cnt++;
            double oldError = Math.abs(equation.calculate(x));
            x = getNextXByIterations(equation, x);
            if (cnt > REASONABLE_CNT) {
                if (Math.abs(equation.calculate(x)) > oldError || cnt > CRITICAL_CNT) {
                    throw new NoLimitException(equation);
                }
            }
        }
        if (Double.isInfinite(x)) {
            throw new NoLimitException(equation);
        }
        return x;
    }

    public static double solve(Equation equation, double persistence) throws NoLimitException{
        Random randomizer = new Random();
        double x0 = randomizer.nextDouble();
        UnaryOperator<Double> func = equation.getFunction();
        int counter = 0;
        while (!(func.apply(x0) > 0 && FunctionOperations.derivative(func).apply(x0) < 0) && ++counter < 100) {
            x0 = (randomizer.nextDouble() - 0.5) * func.apply(x0);
        }
        double x = x0;
        return solve(equation, x, persistence);
    }

    public static double[] solve(EquationSystem system, double persistence, double[] initial) throws
            NoLimitException {
        double[] solution = initial;
        int cnt = 0;
        while (!isPersistenceSatisfied(system.getErrors(solution), persistence)) {
            cnt++;
            if (cnt > CRITICAL_CNT) {
                throw new NoLimitException(system);
            }
            solution = getNextByIterations(system, solution);
        }
        return solution;
    }

    private static boolean isPersistenceSatisfied(double[] errors, double persistence) {
        boolean ret = true;
        for (double e : errors) {
            ret = ret && e < persistence / 10;
        }
        return ret;
    }

    private static double[] getNextByIterations(EquationSystem equationSystem, double[] previous) {
        double[] ret = new double[EQUATIONS_NUMBER];
        for (int i = 0; i < EQUATIONS_NUMBER; i++) {
            ret[i] = previous[i] - equationSystem.calculate(previous)[i];
        }
        return ret;
    }

    private static double getNextXByIterations(Equation equation, double xPrevious) {
        return xPrevious + equation.calculate(xPrevious);
    }
}
