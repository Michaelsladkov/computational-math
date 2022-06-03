package main.java;

import main.java.equations.MyEquations;

import java.util.Scanner;

public class Util {
    public static Double readDouble(Scanner scanner, String caption, boolean positive) {
        System.out.println(caption);
        Double ret = null;
        while (ret == null) {
            String line = scanner.nextLine();
            try {
                ret = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("This is not a number");
                continue;
            }
            if (ret <= 0 && positive) {
                System.out.println("Number should be positive");
                ret = null;
            }
        }
        return ret;
    }

    public static Integer readIndex(Scanner scanner) {
        Integer index = null;
        while (index == null) {
            String line = scanner.nextLine();
            try {
                index = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("This is not a number");
                continue;
            }
            if (index < 1 || index > MyEquations.equations.size()) {
                System.out.println("Number should be between 1 and " + MyEquations.equations.size());
                index = null;
            }
        }
        return index;
    }
}
