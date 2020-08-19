package com.khurshid.kamkora.model;

import com.google.gson.annotations.SerializedName;

public class AddressSingleModelResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("address")
    private Address address;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
