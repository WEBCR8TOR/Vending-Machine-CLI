package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MoneyManagerTest {

    private MoneyManager moneyManager;

    @Before
    public void setUp() {
        this.moneyManager = new MoneyManager();
    }

    @Test
    public void returnChange() {

        String expectedOutput = "Change given: 9 quarters, 1 dimes, 0 nickels.";
        String actualOutput = moneyManager.returnChange(BigDecimal.valueOf(2.35));
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    public void returnNewChange() {

        String expectedOutput = "Change given: 17 quarters, 1 dimes, 1 nickels.";
        String actualOutput = moneyManager.returnChange(BigDecimal.valueOf(4.40));
        assertEquals(expectedOutput, actualOutput);

    }


}