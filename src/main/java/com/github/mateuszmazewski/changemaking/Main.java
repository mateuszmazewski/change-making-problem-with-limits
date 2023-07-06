package com.github.mateuszmazewski.changemaking;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    private final int[] coins = {500, 200, 100, 50, 20, 10, 5, 2, 1};
    private final int[] limits = new int[]{1, 3, 5, 10, 20, 200, 100, 100, 10000};
    private final MachineWithCoins machine = new MachineWithCoins(coins, limits);

    public static void main(String[] args) {
        Main main = new Main();
        boolean isInteractive = args.length == 0;

        if (!isInteractive) {
            for (String s : args) {
                main.getChange(s, false);
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nie podano argumentów wywołania. " +
                    "Uruchamiam tryb interaktywny.\n[Wyjście - q]");
            while (true) {
                System.out.print("Podaj resztę: ");
                String s = scanner.next();
                if (s.equals("q")) {
                    return;
                }
                main.getChange(s, true);
            }
        }
    }

    private void getChange(String change, boolean isInteractive) {
        Optional<int[]> result;
        double d;

        try {
            d = Double.parseDouble(change);
        } catch (NumberFormatException e) {
            System.out.println("Błędny argument: " + change);
            exitIfNotInteractive(isInteractive);
            return;
        }

        try {
            result = machine.getChange(d);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            exitIfNotInteractive(isInteractive);
            return;
        }
        System.out.println("Dla reszty " + MachineWithCoins.roundToTwoDecimalPlaces(d) + " zł:");
        if (result.isPresent()) {
            printResult(result.get(), coins);
        } else {
            System.out.println("Nie można wydać reszty");
        }
        System.out.println();
    }

    private static void exitIfNotInteractive(boolean isInteractive) {
        if (!isInteractive) {
            System.out.println("Przerywam działanie.");
            System.exit(-1);
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
