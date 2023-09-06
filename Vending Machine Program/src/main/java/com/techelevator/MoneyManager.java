package com.techelevator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class MoneyManager {

   private BigDecimal balance = BigDecimal.ZERO.setScale(2);
   private BigDecimal balanceRemaining = BigDecimal.ZERO.setScale(2);
   private BigDecimal totalSales = BigDecimal.ZERO;

   public void feedMoney(Scanner userInput) {

       String inputOption;

      do {
          System.out.println("Would you like to deposit $(1), $(5), or $(10)?");
          inputOption = userInput.nextLine();
      } while (!inputOption.equals("1") && !inputOption.equals("5") && !inputOption.equals("10"));

       BigDecimal inputMoney = new BigDecimal(inputOption);
       addMoney(inputMoney);
       Log.writerLogFeedMoney(inputOption, "Log.txt");

   }


   public String returnChange(BigDecimal amount) {

        BigDecimal quarterValue = new BigDecimal("0.25");
        BigDecimal dimeValue = new BigDecimal("0.10");
        BigDecimal nickelValue = new BigDecimal("0.05");

        int quarters = amount.divide(quarterValue, RoundingMode.DOWN).intValue();
        amount = amount.subtract(quarterValue.multiply(new BigDecimal(quarters)));

        int dimes = amount.divide(dimeValue, RoundingMode.DOWN).intValue();
        amount = amount.subtract(dimeValue.multiply(new BigDecimal(dimes)));

        int nickels = amount.divide(nickelValue, RoundingMode.DOWN).intValue();

        System.out.println("Change given: " +  quarters + " quarters, " + dimes + " dimes, " + nickels + " nickels.");

        Log.writerLogGiveChange(amount, "Log.txt");

        balance = BigDecimal.ZERO;

        balanceRemaining = BigDecimal.ZERO;

        return "Change given: " +  quarters + " quarters, " + dimes + " dimes, " + nickels + " nickels.";
    }

   public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

   public BigDecimal getTotalSales() {
        return totalSales;
    }

   private void addMoney(BigDecimal money){
        balance = balance.add(money);
        balanceRemaining = balanceRemaining.add(money);
    }

   public BigDecimal getBalanceRemaining() {
        return balanceRemaining;
    }

   public void setBalanceRemaining(BigDecimal balanceRemaining) {
        this.balanceRemaining = balanceRemaining;
    }

   public BigDecimal getBalance() {
        return balance;
    }
}
