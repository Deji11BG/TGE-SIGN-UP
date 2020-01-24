package com.example.tgesign_up;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConstantsResponse {

    @SerializedName("constant_id")
    @Expose
    private Integer constants_id;


    @SerializedName("constant_value")
    @Expose
    private String value;

    public ConstantsResponse(Integer constants_id, String value) {
        this.constants_id = constants_id;
        this.value = value;
    }

    public Integer getConstants_id() {
        return constants_id;
    }

    public void setConstants_id(Integer constants_id) {
        this.constants_id = constants_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

