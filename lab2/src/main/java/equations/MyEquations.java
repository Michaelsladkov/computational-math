package main.java.equations;

import java.util.ArrayList;
import java.util.List;

public class MyEquations {
    public static final Equation SIMPLE = new Equation("x^2 - 25 = 0", x -> x*x-25);
    public static final Equation POLYNOMIAL = new Equation("0.1x^3 + 0.2x^2 - 0.5x = 0",
            x -> 0.1 * Math.pow(x, 3) + 0.2 * Math.pow(x, 2) - 0.5 * x);
    public static final Equation TRIGONOMETRICAL = new Equation("sin(2x) + 4(sin(x))^2 = 0",
            x -> Math.sin(2 * x) + 4 * Math.pow(Math.sin(x), 2));
    public static final Equation DEATH_OF_ITERATIONS = new Equation("2x^3 + 5x^2 - 0.5x = 0",
            x -> 2 * Math.pow(x, 3) + 5 * Math.pow(x, 2) - 0.5 * x);

    public final static List<Equation> equations = new ArrayList<>();

    static {
        equations.add(POLYNOMIAL);
        equations.add(TRIGONOMETRICAL);
        equations.add(SIMPLE);
        equations.add(DEATH_OF_ITERATIONS);
    }
}

