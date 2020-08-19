package com.khurshid.kamkora.utils;

import com.khurshid.kamkora.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CentralData {

    public static List<Order> cartList = null;
    public static CentralData centralData = null;

    public static CentralData initialize() {
        if (centralData == null) {
            centralData = new CentralData();
        }
        return centralData;
    }

    public static List<Order> getCartList() {
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        return cartList;
    }
}
