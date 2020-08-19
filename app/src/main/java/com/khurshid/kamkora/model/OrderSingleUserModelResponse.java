package com.khurshid.kamkora.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderSingleUserModelResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("orders")
    private List<Order> orders;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
