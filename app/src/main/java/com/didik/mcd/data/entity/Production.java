package com.didik.mcd.data.entity;

import com.google.gson.annotations.SerializedName;

public class Production {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
