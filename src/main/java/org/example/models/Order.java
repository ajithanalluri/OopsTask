package org.example.models;

import java.time.Instant;
import java.util.List;

public class Order {

    int orderId;
    int customerId;

    List<OrderItem> orderItem;
    Instant timestamp;
    double totalOrderPrice;

    public Order() {
    }

    public Order(int orderId, int customerId, List<OrderItem> orderItem, Instant timestamp, double totalOrderPrice) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItem = orderItem;
        this.timestamp = timestamp;
        this.totalOrderPrice = totalOrderPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(double totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }
}
