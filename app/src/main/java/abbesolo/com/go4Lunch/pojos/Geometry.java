package abbesolo.com.go4Lunch.pojos;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.pojos. All rights reserved.
//
public class Geometry {

    @SerializedName("location")
    @Expose
    private Location location;

    public Location getLocation() {
        return location;
    }
}
