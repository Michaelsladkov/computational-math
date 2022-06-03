package main.java.equations;

import java.util.function.UnaryOperator;

public class Equation {
    private final String stringRepresentation;
    private final UnaryOperator<Double> function;

    public Equation(String stringRepresentation, UnaryOperator<Double> function) {
        this.stringRepresentation = stringRepresentation;
        this.function = function;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    public double calculate(double arg) {
        return function.apply(arg);
    }

    public UnaryOperator<Double> getFunction() {
        return function;
    }
}
