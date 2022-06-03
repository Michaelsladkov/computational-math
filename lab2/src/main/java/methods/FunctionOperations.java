package main.java.methods;

import java.util.function.UnaryOperator;

public class FunctionOperations {
    private static final double EPS = 1e-7;
    public static UnaryOperator<Double> derivative(UnaryOperator<Double> function) {
        return x -> (function.apply(x + EPS) - function.apply(x))/EPS;
    }
}
