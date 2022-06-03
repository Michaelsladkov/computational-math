package main.java.methods;

import main.java.equations.Equation;

import java.util.Random;
import java.util.function.UnaryOperator;

public class TangentsMethod {
    public static double solve(Equation equation, double persistence) {
        Random randomizer = new Random();
        double x0 = randomizer.nextDouble();
        UnaryOperator<Double> func = equation.getFunction();
        UnaryOperator<Double> funcSecondDerivative = FunctionOperations.derivative(FunctionOperations.derivative(func));
        while (func.apply(x0) * funcSecondDerivative.apply(x0) <= 0) {
            x0 = (randomizer.nextDouble() - 0.5) * 20 * func.apply(x0);
        }
        double x = x0;
        while (Math.abs(equation.calculate(x)) > persistence / 10) {
            x = getNextXByNewton(equation, x);
        }
        if (Double.isNaN(x)) {
            return solve(equation, persistence);
        }
        return x;
    }

    private static double getNextXByNewton(Equation equation, double x) {
        double k = FunctionOperations.derivative(equation.getFunction()).apply(x);
        double b = equation.calculate(x);
        return x-b/k;
    }
}
