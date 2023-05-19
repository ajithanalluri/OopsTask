package org.example.models;

import java.util.List;

public class Cart {

    List<OrderItem> foodsList;

    public Cart(List<OrderItem> foodsList) {
        this.foodsList = foodsList;
    }

    public List<OrderItem> getFoodsList() {
        return foodsList;
    }

    public void setFoodsList(List<OrderItem> foodsList) {
        this.foodsList = foodsList;
    }
}
