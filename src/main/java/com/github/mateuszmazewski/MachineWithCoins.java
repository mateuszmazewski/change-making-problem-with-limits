package com.github.mateuszmazewski;

import java.util.Arrays;
import java.util.Optional;

public class MachineWithCoins {
    private static final int MAX = Integer.MAX_VALUE - 1;
    private final int[] coins;
    private final int[] limits;

    public MachineWithCoins(int[] coins, int[] limits) {
        validateArgs(coins, limits);
        this.coins = Arrays.copyOf(coins, coins.length);
        this.limits = Arrays.copyOf(limits, limits.length);
    }

    private void validateArgs(int[] coins, int[] limits) {
        if (coins == null || coins.length == 0) {
            throw new IllegalArgumentException("Brak zdefiniowanych monet");
        }
        if (limits == null || limits.length == 0) {
            throw new IllegalArgumentException("Brak zdefiniowanych limitów ilościowych dla monet");
        }
        if (coins.length != limits.length) {
            throw new IllegalArgumentException("Monet i ich limitów musi być tyle samo");
        }
        for (int c : coins) {
            if (c < 1) {
                throw new IllegalArgumentException("Nieprawidłowa moneta: " + c);
            }
        }
        for (int l : limits) {
            if (l < 0) {
                throw new IllegalArgumentException("Nieprawidłowy limit: " + l);
            }
        }
    }

    /**
     * @param change change to be given. It is rounded to two decimal places. Has to be >= 0.01.
     * @return Optional describing the array of integers that shows how many coins of each type should be used
     * in order to give change using the minimum number of coins or an empty Optional if the change cannot be given
     * using current limits.
     * @throws IllegalArgumentException if change is not a positive number.
     * @implNote Uses dynamic programming technique. Number of coins of each type is limited
     * and updated over subsequent calls.
     */
    public Optional<int[]> getChange(double change) {
        change = roundToTwoDecimalPlaces(change);
        if (change < 0.01) {
            throw new IllegalArgumentException("Kwota musi być dodatnią liczbą zmiennoprzecinkową >= 0.01");
        }
        int targetAmount = (int) (change * 100);
        int[][] usedCoins = new int[targetAmount + 1][coins.length];
        int[] howManyCoinsNeeded = new int[targetAmount + 1];
        Arrays.fill(howManyCoinsNeeded, 1, targetAmount + 1, MAX);
        for (int[] row : usedCoins) {
            Arrays.fill(row, 0);
        }
        int[] limitsCopy = Arrays.copyOf(limits, limits.length);

        for (int coinIdx = 0; coinIdx < coins.length; coinIdx++) {
            while (limitsCopy[coinIdx] > 0) {
                for (int amount = targetAmount; amount >= 0; amount--) {
                    int newAmount = amount + coins[coinIdx];
                    if (newAmount <= targetAmount && howManyCoinsNeeded[amount] + 1 < howManyCoinsNeeded[newAmount]) {
                        howManyCoinsNeeded[newAmount] = howManyCoinsNeeded[amount] + 1;
                        usedCoins[newAmount] = Arrays.copyOf(usedCoins[amount], coins.length);
                        usedCoins[newAmount][coinIdx]++;
                    }
                }
                limitsCopy[coinIdx]--;
            }
        }

        if (howManyCoinsNeeded[targetAmount] == MAX) {
            return Optional.empty();
        } else {
            for (int coinIdx = 0; coinIdx < limits.length; coinIdx++) {
                limits[coinIdx] -= usedCoins[targetAmount][coinIdx];
            }
            return Optional.of(usedCoins[targetAmount]);
        }
    }

    public static double roundToTwoDecimalPlaces(double d) {
        return Math.round(d * 100) / 100.0;
    }

    public int[] getLimits() {
        return limits.clone();
    }
}
