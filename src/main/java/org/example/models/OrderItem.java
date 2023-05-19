package org.example.models;

public class OrderItem {

    String orderItemName;
    int orderItemPieces;
    int orderItemPrice;

    public OrderItem(String orderItemName, int orderItemPieces, int orderItemPrice) {
        this.orderItemName = orderItemName;
        this.orderItemPieces = orderItemPieces;
        this.orderItemPrice = orderItemPrice;
    }

    public String getOrderItemName() {
        return orderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        this.orderItemName = orderItemName;
    }

    public int getOrderItemPieces() {
        return orderItemPieces;
    }

    public void setOrderItemPieces(int orderItemPieces) {
        this.orderItemPieces = orderItemPieces;
    }

    public int getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(int orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }
}
