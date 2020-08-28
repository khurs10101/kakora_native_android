package com.khurshid.kamkora.model;

import com.google.gson.annotations.SerializedName;

public class Ads {

    @SerializedName("_id")
    private String id;
    private String serviceId;
    private String url;
    private int sampleImage;
    private String adsType;

    public String getAdsType() {
        return adsType;
    }

    public void setAdsType(String adsType) {
        this.adsType = adsType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getSampleImage() {
        return sampleImage;
    }

    public void setSampleImage(int sampleImage) {
        this.sampleImage = sampleImage;
    }
}
