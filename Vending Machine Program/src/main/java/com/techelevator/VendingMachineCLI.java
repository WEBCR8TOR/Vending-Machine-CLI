package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VendingMachineCLI {

	private final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	public static final String ITEM_FILE_ERROR_MESSAGE = "Item File Not Found. Please call 1 (800) VENDFIX for assistance and provide Vendo-Matic 800 ID# \"Team4\". Thank you!";
	private final MoneyManager MONEY_MANAGER = new MoneyManager();
	private final ItemManager ITEM_MANAGER = new ItemManager(MONEY_MANAGER);
	private final String fileNameDefined = "main.csv";
	private final File file = new File(fileNameDefined);
	Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		VendingMachineCLI cli = new VendingMachineCLI();
		cli.run();

	}

	public void run() {

		//Save return string from mainMenu method as choice, denoting what the user has chosen
		String choice = mainMenu();

		loadVendingItems(file);

		do {

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

				ITEM_MANAGER.displayItems();

				choice = mainMenu();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

				choice = purchaseMenu();

				do {

					if (choice.equals("Feed Money")) {

						MONEY_MANAGER.feedMoney(userInput);

					} else if (choice.equals("Select Product")) {

						ITEM_MANAGER.displayItems();
						
						ITEM_MANAGER.purchaseItem(userInput);


					} else {

						MONEY_MANAGER.returnChange(MONEY_MANAGER.getBalanceRemaining());
						break;

					}

					choice = purchaseMenu();

				} while (choice.equals("Feed Money") || choice.equals("Select Product") || choice.equals("Finish Transaction"));

				choice = mainMenu();

			}



		}	while (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS) || choice.equals(MAIN_MENU_OPTION_PURCHASE));

		System.out.println("Thank you, goodbye!");
		System.exit(0);
	}

	public String mainMenu () {

		//Declare Sting variable and the display options into the consol and loop them until user inputs a 1, 2, or 3
		//into inputOption

		String inputOption;
		do {

			System.out.println();
			System.out.println("********** Vendo-Matic 800 **********");
			System.out.println();
			System.out.println("(1) Display Vending Machine Items");
			System.out.println("(2) Purchase");
			System.out.println("(3) Exit");
			inputOption = userInput.nextLine();

		} while(!inputOption.equals("1") && !inputOption.equals("2") && !inputOption.equals("3") && !inputOption.equals("4"));

		//Save inputOption to conform with instance variables

		if (inputOption.equals("1")) {

			inputOption = "Display Vending Machine Items";

		} else if (inputOption.equals("2")) {

			inputOption = "Purchase";

		} else if (inputOption.equals("4")) {

			Log.writerTotalSales();

		} else {

			inputOption = "Exit";

		}

		return inputOption;
	}

	public String purchaseMenu () {

		//Declare Sting variable and the display options into the console and loop them until user inputs a 1, 2, or 3
		//into inputOption

		String inputOption;

		do {

			System.out.println();
			System.out.println("Current Money Provided: $" + MONEY_MANAGER.getBalanceRemaining());
			System.out.println("Total Spent: $" + (MONEY_MANAGER.getBalance().subtract(MONEY_MANAGER.getBalanceRemaining())));
			System.out.println();
			System.out.println("(1) Feed Money");
			System.out.println("(2) Select Product");
			System.out.println("(3) Finish Transaction");
			inputOption = userInput.nextLine();

		} while(!inputOption.equals("1") && !inputOption.equals("2") && !inputOption.equals("3"));

		//Save inputOption to conform with instance variables

		if (inputOption.equals("1")) {

			inputOption = "Feed Money";

		} else if (inputOption.equals("2")) {


			inputOption = "Select Product";

		} else {

			inputOption = "Finish Transaction";

		}

		return inputOption;
	}

private void loadVendingItems(File file){
	try {

		//Check the file for the correct formatting. If it's not the correct format, the methods throws a NumberFormatException
		ITEM_MANAGER.isFileInFormat(file);

		//With a correct formatted file, add the items to the Map itemMap
		ITEM_MANAGER.addItemToMap();

	} catch (FileNotFoundException e) {

		System.out.println(ITEM_FILE_ERROR_MESSAGE);
		System.exit(0);

	} catch (NumberFormatException e) {

		System.out.println("File Format Issue. Please call 1 (800) VENDFIX for assistance and provide Vendo-Matic 800 ID# \"Team4\". Thank you!");
		System.exit(0);
	}
}

}
