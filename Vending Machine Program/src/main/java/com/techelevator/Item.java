package com.techelevator;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Item implements Purchasable{


    private final String LOCATION;
    private final String NAME;
    private final BigDecimal COST;
    private int quantity = 5;
    private final String TYPE;
    private int standardItemCount = 0;
    private int BOGODOItemCount = 0;
    private Map<String, Item> itemMap = new LinkedHashMap<>();
    private String enjoymenStatment = "";


    public Item(String location, String name, BigDecimal cost, String type) {
        this.LOCATION = location;
        this.NAME = name;
        this.COST = cost;
        this.TYPE = type;

        if (type.equals("Munchy")) {
            this.enjoymenStatment = "Crunch Crunch, Yum!";
        } else if (type.equals("Candy")) {
            this.enjoymenStatment = "Yummy Yummy, So Sweet!";
        } else if (type.equals("Drink")) {
            this.enjoymenStatment = "Glug Glug, Yum!";
        } else {
            this.enjoymenStatment = "Chew Chew, Yum!";
        }

    }

    public String getEnjoymenStatment() {
        return enjoymenStatment;
    }

    public int getStandardItemCount() {
        return standardItemCount;
    }

    public void setStandardItemCount(int standardItemCount) {
        this.standardItemCount = standardItemCount;
    }

    public int getBOGODOItemCount() {
        return BOGODOItemCount;
    }

    public void setBOGODOItemCount(int BOGODOItemCount) {
        this.BOGODOItemCount = BOGODOItemCount;
    }

    public String getType() {
        return TYPE;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return LOCATION;
    }

    public String getName() {
        return NAME;
    }

    public BigDecimal getCost() {
        return COST;
    }

    public int getQuantity() {
        return quantity;
    }

}


