package main.java.equations;

public class FourArgumentsEquation {
    private final String stringRepresentation;
    private final FourArgumentsFunction<Double> function;

    public FourArgumentsEquation(String representation, FourArgumentsFunction<Double> function) {
        this.function = function;
        stringRepresentation = representation;
    }

    public double calculate(double[] args) {
        return function.apply(args[0], args[1], args[2], args[3]);
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    public FourArgumentsFunction<Double> getFunction(){
        return function;
    }
}
