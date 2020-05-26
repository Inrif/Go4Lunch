package abbesolo.com.go4Lunch.apiGoogleMap;


import java.util.Map;

import abbesolo.com.go4Lunch.pojos.Detail;
import abbesolo.com.go4Lunch.pojos.RestaurantsResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

//
// Created by Hounsa Romuald on 2020-03-17.
// Copyright (c) 2020 abbesolo.com.go4Lunch.apiGoogleMap. All rights reserved.
//
public interface RestaurantService {

    /**
     * GET call for retrofit
     *
     * @param parameters map of parameters
     * @return Observable Restaurant result
     */
    @GET("nearbysearch/json?")
    Observable<RestaurantsResult> getNearByRestaurant(@QueryMap Map<String, String> parameters);

    /**
     * GET call for retrofit
     *
     * @param parameters map of parameters
     * @return Observable Detail result
     */
    @GET("details/json?fields=name,rating,formatted_address,formatted_phone_number,photos,website")
    Observable<Detail> getDetailRestaurant(@QueryMap Map<String, String> parameters);
}
