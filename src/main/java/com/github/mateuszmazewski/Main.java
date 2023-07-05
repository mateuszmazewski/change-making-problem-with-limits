package com.github.mateuszmazewski;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Nie podano argumentów. Przerywam działanie. " +
                    "Argumenty muszą być dodatnimi liczbami zmiennoprzecinkowymi >= 0.01");
            return;
        }

        int[] coins = {500, 200, 100, 50, 20, 10, 5, 2, 1};
        int[] limits = new int[]{1, 3, 5, 10, 20, 200, 100, 100, 10000};
        MachineWithCoins machine = new MachineWithCoins(coins, limits);
        Optional<int[]> result;
        double d;

        for (String s : args) {
            try {
                d = Double.parseDouble(s);
                d = Math.round(d * 100) / 100.0; // Round to two decimal places
                if (d < 0.01) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.err.println("Błędny argument: " + s);
                System.err.println("Przerywam działanie. Argumenty muszą być dodatnimi liczbami zmiennoprzecinkowymi >= 0.01");
                return;
            }

            result = machine.getChange(d);
            System.out.println("Dla reszty " + d + " zł:");
            if (result.isPresent()) {
                printResult(result.get(), coins);
            } else {
                System.out.println("Nie można wydać reszty");
            }
            System.out.println();
        }

    }

    private static void printResult(int[] usedCoins, int[] coins) {
        for (int i = 0; i < coins.length; i++) {
            if (usedCoins[i] > 0) {
                boolean gr = coins[i] < 100;
                System.out.println("Wydaj " + usedCoins[i] + " monet " +
                        (gr ? coins[i] : coins[i] / 100)
                        + (gr ? " gr" : " zł"));
            }
        }
    }

}
