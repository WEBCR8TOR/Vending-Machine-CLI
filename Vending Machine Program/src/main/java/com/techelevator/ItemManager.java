package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class ItemManager {

    public static final Map<String, Item> ITEM_MAP = new LinkedHashMap<>();
    private MoneyManager moneyM;
    private int itemPurchasedCount = 0;
    public final String ITEM_FILE_ERROR_MESSAGE = "Item File Not Found. Please call 1 (800) VENDFIX for assistance and provide Vendo-Matic 800 ID# \"Team4\". Thank you!";


    public ItemManager(MoneyManager money_m) {
        this.moneyM = money_m;
    }

    public ItemManager() {}

    public void addItemToMap() throws FileNotFoundException {

        Scanner scanFile = new Scanner(new File("main.csv"));

        while (scanFile.hasNextLine()) {
            String line = scanFile.nextLine();
            String[] parts = line.split(",");
            String slotLocation = parts[0];
            String productName = parts[1];
            BigDecimal price = new BigDecimal(parts[2]);
            String type = parts[3];
            Item newItem = new Item(slotLocation, productName, price, type);
            ITEM_MAP.put(slotLocation, newItem);
        }
        scanFile.close();
    }

    public void isFileInFormat(File fileToCheck) throws NumberFormatException{

        try (Scanner inputStream = new Scanner(fileToCheck)) {

            while(inputStream.hasNextLine()) {

                //read single line, put in string, split string into an array
                String data = inputStream.nextLine();
                String[] dataSplit = data.split(",");

                if (dataSplit.length != 4) {
                    throw new NumberFormatException();
                }

                if (isNumeric(dataSplit[1])) {
                    throw new NumberFormatException();
                }

                if (!isNumeric(dataSplit[2])) {
                    throw new NumberFormatException();
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println(ITEM_FILE_ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public boolean isNumeric(String str) {

        if (str  == null) {
            return false;
        }

        try {
            double check = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            System.out.println("ERROR: Empty data point. Please review file data for accuracy.");
        }
        return true;
    }

    public void displayItems() {
        System.out.println();
        System.out.println("Current Items:");
		System.out.println();

        for (Map.Entry<String, Item> entry : ITEM_MAP.entrySet()) {
            System.out.println("| " + entry.getValue().getLocation() + " | " + entry.getValue().getName() + " | " + entry.getValue().getCost() + " | " + entry.getValue().getQuantity());
        }

        System.out.println();
    }

    public void purchaseItem(Scanner userInput) {

        String inputOption;

        //Get location of item to purchase from user
        System.out.println("What is the location of the item you'd like to purchase?");
        inputOption = userInput.nextLine();

        //Evaluate whether the inputOption is a valid location. If not, notify the user and return to the purchase menu
        //If it is a valid location, check whether the item is sold out or not. If sold out, notify the user and revert
        //to the purchase menu. If the item is available, dispense the item and print the name, cost, money remaining, and
        // depending on the item classification, return an "action" string ie "Crunch Crunch, Yum!"

        if (ITEM_MAP.keySet().contains(inputOption)) {

            if (ITEM_MAP.get(inputOption).getQuantity() == 0) {

                System.out.println("This item is SOLD OUT. Sorry!");

            } else {
               dispenseItem(inputOption);

            }

        } else {
            System.out.println("The location you've entered is not valid.");
        }

    }

    public void dispenseItem(String inputOption) {

        BigDecimal balance = moneyM.getBalanceRemaining();
        BigDecimal itemCost;
        int bigDecimalCheck = ITEM_MAP.get(inputOption).getCost().subtract(moneyM.getBalanceRemaining()).compareTo(BigDecimal.ZERO);

        //Check to make sure there are sufficient funds to purchase item
        if (bigDecimalCheck == 1) {

            System.out.println("Insufficient Funds. Please feed more money.");

        } else {
            //account for BOGODO on every other item purchased getting a $1 discount and add total to total sales.
            //increase item counts on appropriate items, ie BOGODO or standard and total sold item count.
            if (itemPurchasedCount % 2 != 0) {
                itemCost = ITEM_MAP.get(inputOption).getCost();
                itemCost = itemCost.subtract(BigDecimal.ONE);

                BigDecimal updateSales = itemCost.add(moneyM.getTotalSales());
                moneyM.setTotalSales(updateSales);

                int newBogodoCount = ITEM_MAP.get(inputOption).getBOGODOItemCount() + 1;
                ITEM_MAP.get(inputOption).setBOGODOItemCount(newBogodoCount);

            } else {

                itemCost = ITEM_MAP.get(inputOption).getCost();

                BigDecimal updateSales = itemCost.add(moneyM.getTotalSales());
                moneyM.setTotalSales(updateSales);

                int newCount = ITEM_MAP.get(inputOption).getStandardItemCount() + 1;
                ITEM_MAP.get(inputOption).setStandardItemCount(newCount);
            }

            BigDecimal newBalance = balance.subtract(itemCost);
            moneyM.setBalanceRemaining(newBalance);
            itemPurchasedCount++;

            int newQuantity = ITEM_MAP.get(inputOption).getQuantity() - 1;
            ITEM_MAP.get(inputOption).setQuantity(newQuantity);
            Log.writerLogItem(inputOption, "Log.txt");


            //Print out enjoyment statements associated with each treat type.
            System.out.println("You've purchased: | " + ITEM_MAP.get(inputOption).getName() + " | " + ITEM_MAP.get(inputOption).getCost() + " | Remaining Balance: $" + moneyM.getBalanceRemaining());
            System.out.println(ITEM_MAP.get(inputOption).getEnjoymenStatment());

        }
    }
}
