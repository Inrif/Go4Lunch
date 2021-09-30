package abbesolo.com.go4Lunch.googleMap;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import abbesolo.com.go4Lunch.models.DetailRestaurant;
import abbesolo.com.go4Lunch.models.Restaurant;

//
// Created by Hounsa Romuald on 2020-03-17.
// Copyright (c) 2020 abbesolo.com.go4Lunch.googleMap. All rights reserved.
//
public interface NearbyPlaces {

    MutableLiveData<ArrayList<Restaurant>> configureRetrofitCall(LatLng latLng, String radius, String type);

    LiveData<DetailRestaurant> configureDetailRestaurant(String placeId);
}
