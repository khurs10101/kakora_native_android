package com.khurshid.kamkora.model;

import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("_id")
    private String id;
    private String name;
    private String url;
    private String serviceId;
    private String updatedAt;
    private int sampleImage;


    public int getSampleImage() {
        return sampleImage;
    }

    public void setSampleImage(int sampleImage) {
        this.sampleImage = sampleImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
