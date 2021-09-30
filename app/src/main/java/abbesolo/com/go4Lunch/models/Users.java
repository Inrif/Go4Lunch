package abbesolo.com.go4Lunch.models;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.models. All rights reserved.
//
public class Users {
    // FIELDS
    private String name;
    private String avatarUrl;
    private String restaurantName;
    private String placeId;
    private String uid;

    //constructors
    public Users() {
    }

    public Users(String name, String avatarUrl, String resto, String placeId, String uid) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.restaurantName = resto;
        this.placeId = placeId;
        this.uid = uid;
    }

    //GETTER AND SETTER
    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.name = uid;
    }
}
