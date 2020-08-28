package com.khurshid.kamkora.model;

import java.io.Serializable;

public class LocalLocation implements Serializable {
    private String likelyAddress;
    private Double latitude;
    private Double longitude;

    public String getLikelyAddress() {
        return likelyAddress;
    }

    public void setLikelyAddress(String likelyAddress) {
        this.likelyAddress = likelyAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
