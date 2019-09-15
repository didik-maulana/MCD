package com.didik.mcd.data.entity;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

}
