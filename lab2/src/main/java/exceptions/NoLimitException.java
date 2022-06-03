package main.java.exceptions;

import main.java.equations.Equation;
import main.java.equations.EquationSystem;

public class NoLimitException extends Exception{
    public NoLimitException(Equation e) {
        super("No iterations limit for " + e.toString());
    }

    public NoLimitException(EquationSystem e) {
        super("No iterations limit for \n" + e.toString());
    }
}
