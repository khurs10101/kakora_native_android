package com.khurshid.kamkora.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllServiceResponseModel {

    private String message;
    @SerializedName("services")
    private List<Service> serviceList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
