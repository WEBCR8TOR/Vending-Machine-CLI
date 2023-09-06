package com.techelevator;

import java.math.BigDecimal;

public interface Purchasable {

    void setQuantity(int quantity);
    String getLocation();
    String getName();
    BigDecimal getCost();
    int getQuantity();


}
