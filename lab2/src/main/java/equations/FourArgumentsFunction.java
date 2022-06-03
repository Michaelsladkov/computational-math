package main.java.equations;

@FunctionalInterface
public interface FourArgumentsFunction<T> {
    double apply(T x1, T x2, T x3, T x4);
}
