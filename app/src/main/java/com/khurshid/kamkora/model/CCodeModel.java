package com.khurshid.kamkora.model;

import com.google.gson.annotations.SerializedName;

public class CCodeModel {
    @SerializedName("ccode")
    private String ccode;

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }
}
