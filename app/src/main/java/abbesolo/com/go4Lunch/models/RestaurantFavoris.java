package abbesolo.com.go4Lunch.models;

//
// Created by  on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.models. All rights reserved.
//
public class RestaurantFavoris {
    //FIELDS
    private String uid;
    private String name;
    private String placeId;
    private String address;
    private String photoReference;
    private double rating;

    //constructor
    public RestaurantFavoris(String uid, String name, String placeId, String address, String photoReference, double rating) {
        this.uid = uid;
        this.name = name;
        this.placeId = placeId;
        this.address = address;
        this.photoReference = photoReference;
        this.rating = rating;
    }

    /**
     * constructor needed to firebase
     */
    @SuppressWarnings("constructor needed to firebase")
    public RestaurantFavoris() {
    }

    //GETTER AND SETTER
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
