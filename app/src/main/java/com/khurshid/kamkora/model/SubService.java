package com.khurshid.kamkora.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubService implements Serializable {

    @SerializedName("_id")
    private String id;
    private String name;
    private String subServiceId;
    private String serviceId;
    private String url;
    private String updatedAt;
    private String rate;
    private int sampleImage;
    private int sampleImageCarousel;

    public int getSampleImageCarousel() {
        return sampleImageCarousel;
    }

    public void setSampleImageCarousel(int sampleImageCarousel) {
        this.sampleImageCarousel = sampleImageCarousel;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

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

    public String getSubServiceId() {
        return subServiceId;
    }

    public void setSubServiceId(String subServiceId) {
        this.subServiceId = subServiceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
