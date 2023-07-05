package com.github.mateuszmazewski;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MachineWithCoinsTest {

    private static int[] coins, limits;
    private MachineWithCoins machine;

    @BeforeAll
    static void beforeAll() {
        coins = new int[]{500, 200, 100, 50, 20, 10, 5, 2, 1};
        limits = new int[]{1, 3, 5, 10, 20, 200, 100, 100, 10000};
    }

    @BeforeEach
    void setUp() {
        machine = new MachineWithCoins(coins, limits);
    }

    @Test
    @DisplayName("should round given change if it has more than 2 decimal places")
    void getChange_when_moreThanTwoDecimalPlaces_should_roundAndReturnProperResults() {
        int[] expectedResult = new int[]{0, 0, 1, 0, 1, 1, 0, 0, 0};

        Optional<int[]> result = machine.getChange(1.303456);

        assertTrue(result.isPresent());
        assertArrayEquals(expectedResult, result.get());
    }

    @Test
    @DisplayName("should remember number of coins left in the machine after running")
    void getChange_when_called_should_rememberNumberOfCoinsLeft() {
        int[] expectedLimits = {1, 3, 4, 10, 19, 199, 100, 100, 10000};

        machine.getChange(1.30);

        assertArrayEquals(expectedLimits, machine.getLimits());
    }

    @Test
    @DisplayName("should return proper results when called multiple times")
    void getChange_when_multipleCalls_should_returnProperResults() {
        int[] expected1 = new int[]{0, 0, 1, 0, 1, 1, 0, 0, 0};
        int[] expected2 = new int[]{1, 3, 0, 1, 1, 0, 0, 0, 0};
        int[] expected3 = new int[]{0, 0, 4, 5, 1, 0, 0, 0, 0};
        int[] expected4 = new int[]{0, 0, 0, 4, 11, 1, 0, 0, 0};


        Optional<int[]> result1 = machine.getChange(1.30);
        Optional<int[]> result2 = machine.getChange(11.70);
        Optional<int[]> result3 = machine.getChange(6.70);
        Optional<int[]> result4 = machine.getChange(4.30);

        Stream.of(result1, result2, result3, result4)
                .forEach(r -> assertTrue(r.isPresent()));

        assertArrayEquals(expected1, result1.get());
        assertArrayEquals(expected2, result2.get());
        assertArrayEquals(expected3, result3.get());
        assertArrayEquals(expected4, result4.get());
    }

    @Test
    @DisplayName("should return an empty Optional if cannot give the change")
    void getChange_when_cannotGiveChange_should_returnEmptyOptional() {
        int[] coins = {500, 200, 100, 50, 20, 10, 5, 2, 1};
        int[] limits = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1};
        machine = new MachineWithCoins(coins, limits);

        Optional<int[]> result = machine.getChange(20.00);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("should throw IllegalArgumentException when given change is negative")
    void getChange_when_negativeArgument_should_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> machine.getChange(-3.55));
    }
}