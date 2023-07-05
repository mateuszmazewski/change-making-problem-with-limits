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

    public Optional<int[]> getChange(double change) {
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

}