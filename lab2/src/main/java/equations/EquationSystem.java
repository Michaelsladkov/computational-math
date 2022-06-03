package main.java.equations;

public class EquationSystem {
    private final FourArgumentsEquation[] equations;

    public EquationSystem(FourArgumentsEquation[] equations) {
        this.equations = equations;
    }

    public double[] calculate(double[] args) {
        double[] ret = new double[4];
        for(int i = 0; i < 4; i++) {
            ret[i] = equations[i].calculate(args);
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(FourArgumentsEquation e : equations) {
            stringBuilder.append('|').append(e).append('\n');
        }
        return stringBuilder.toString();
    }

    public double[] getErrors(double[] solution) {
        double[] errors = new double[4];
        for (int i = 0; i < 4; i++) {
            errors[i] = Math.abs(equations[i].calculate(solution));
        }
        return errors;
    }
}
