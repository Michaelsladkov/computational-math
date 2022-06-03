package main.java.equations;

import java.util.ArrayList;
import java.util.List;

public class MySystems {
    public static final EquationSystem SIMPLE = new EquationSystem(new FourArgumentsEquation[]{
            new FourArgumentsEquation("(0.1x)^3 - 1 = 0",
                    (x, y, z, t) -> Math.pow(0.1 * x, 3) - 1),
            new FourArgumentsEquation("(0.2y)^5 - 1 = 0",
                    (x, y, z, t) -> Math.pow(0.02 * y, 5) - 1),
            new FourArgumentsEquation("(0.1z)^2 - 4 = 0",
                    (x, y, z, t) -> Math.pow(0.1 * z, 2) - 4),
            new FourArgumentsEquation("0.2t - 17 = 0",
                    (x, y, z, t) -> 0.2 * t - 17)
    });
    public static final EquationSystem LOGARITHMIC = new EquationSystem(new FourArgumentsEquation[]{
            new FourArgumentsEquation("ln(x) - 2ln(y) = 0",
                   (x, y, z, t) -> Math.log(x) - 2 * Math.log(y)),
            new FourArgumentsEquation("ln(y) - 3 = 0",
                    (x, y, z, t) -> Math.log(y) - 3),
            new FourArgumentsEquation("ln(z) - 2ln(t) = 0",
                    (x, y, z, t) -> Math.log(z) - 2 * Math.log(t)),
            new FourArgumentsEquation("ln(t) - 3 = 0",
                    (x, y, z, t) -> Math.log(t) - 3),
    });
    public final static List<EquationSystem> equationSystems = new ArrayList<>();

    static {
        equationSystems.add(LOGARITHMIC);
        equationSystems.add(SIMPLE);
    }
}
