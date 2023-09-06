package com.techelevator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static com.techelevator.ItemManager.ITEM_MAP;

public class Log {

    public static final String ITEM_FILE_ERROR_MESSAGE = "Item File Not Found. Please call 1 (800) VENDFIX for assistance and provide Vendo-Matic 800 ID# \"Team4\". Thank you!";
    private static MoneyManager moneyM = new MoneyManager();

    public void Log(MoneyManager moneyManager){
        this.moneyM = moneyManager;
    }

    public static void writerLogItem(String location,String filePath) {

        try {
            PrintWriter vendingMachineLog = new PrintWriter(new FileOutputStream(filePath, true));
            String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new java.util.Date());
            vendingMachineLog.println(timeStamp + " " + ITEM_MAP.get(location).getName() + " " + ITEM_MAP.get(location).getLocation() + " $" + ITEM_MAP.get(location).getCost() + " $" +  moneyM.getBalanceRemaining().subtract((ITEM_MAP.get(location).getCost())));

        } catch (IOException e) {

            System.out.println(ITEM_FILE_ERROR_MESSAGE);

        }
    }

    public static void writerTotalSales() {

        //Set formatter variable as DTF and in pattern needed. Set filename to String in DTF needed. create file with stated filename.
        DateTimeFormatter formatter;
        formatter = DateTimeFormatter.ofPattern("hh_mm_ss a - MMMM dd yyyy");
        String fileName = LocalDateTime.now().format(formatter);
        File reportFile = new File(fileName);

        //Wrap printwriter in try as to handle and unchecked exception that may occur, then us a foreach loop to iterate through the itemMap Map and display the needed
        //item name, how many items were sold at full price, then how many items were sold at BOGODO price. Lastly, print total sales.
        try (PrintWriter vendingMachineLog = new PrintWriter(new FileOutputStream(reportFile,false),true)) {
            for (String eachItem : ITEM_MAP.keySet()) {
                vendingMachineLog.println( ITEM_MAP.get(eachItem).getName() + " | " + ITEM_MAP.get(eachItem).getStandardItemCount() + " | " + ITEM_MAP.get(eachItem).getBOGODOItemCount());
            }
            vendingMachineLog.println("");
            vendingMachineLog.println("*** TOTAL SALES: " + moneyM.getTotalSales() + " ***");
        } catch (IOException e) {

            System.out.println(ITEM_FILE_ERROR_MESSAGE);

        }
    }

    public static void writerLogFeedMoney(String amount, String filePath) {

        try (PrintWriter vendingMachineLog = new PrintWriter(new FileOutputStream("Log.txt", true))) {

            String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new java.util.Date());
            vendingMachineLog.println(timeStamp + " FEED MONEY: $" + amount + " $" + moneyM.getBalanceRemaining().add(new BigDecimal(amount)));

        } catch (IOException e) {

            System.out.println(ITEM_FILE_ERROR_MESSAGE);

        }
    }

    public static void writerLogGiveChange(BigDecimal amount, String filePath) {

        try (PrintWriter vendingMachineLog = new PrintWriter(new FileOutputStream("Log.txt", true))) {

            String timeStamp = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new java.util.Date());
            vendingMachineLog.println(timeStamp + " GIVE CHANGE: $" + moneyM.getBalanceRemaining() + " $" + BigDecimal.ZERO);

        } catch (IOException e) {

            System.out.println(ITEM_FILE_ERROR_MESSAGE);

        }
    }
}
