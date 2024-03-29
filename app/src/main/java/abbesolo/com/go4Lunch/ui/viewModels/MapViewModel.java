package abbesolo.com.go4Lunch.ui.viewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.googleMap.RepositoryRestaurantList;
import abbesolo.com.go4Lunch.models.DetailRestaurant;
import abbesolo.com.go4Lunch.models.Poi;
import abbesolo.com.go4Lunch.models.Restaurant;
import abbesolo.com.go4Lunch.utils.Utils;


//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.ui.viewModels. All rights reserved.
//
public class MapViewModel extends ViewModel {

    //FIELD
    private final RepositoryRestaurantList mRepositoryRestaurantList = new RepositoryRestaurantList ();

    /**
     * generate user poi
     *
     * @param lat latitude
     * @param lng longitude
     * @return Poi
     */
    public Poi generateUserPoi(double lat, double lng) {
        return new Poi (
                String.valueOf (R.string.my_position),
                "",
                lat,
                lng
        );
    }

    /**
     * generate list Poi with restaurant list
     *
     * @param restaurants     list
     * @param mUsersArrayList list workers
     * @return list of poi
     */
    public List<Poi> generatePois(ArrayList<Restaurant> restaurants, ArrayList<abbesolo.com.go4Lunch.models.Users> mUsersArrayList) {
        List<Poi> pois = new ArrayList<> ();
        List<Restaurant> restaurants1 = Utils.getChoicedRestaurants (restaurants, mUsersArrayList);

        for (Restaurant resto : restaurants1) {
            Poi p = new Poi (
                    resto.getName (),
                    resto.getPlaceId (),
                    resto.getLocation ().getLat (),
                    resto.getLocation ().getLng ()
            );

            if (resto.isChoice ()) {
                p.setChoosen (true);
            }

            pois.add (p);
        }
        return pois;
    }

    /**
     * retrofit call to get all restaurants
     *
     * @param latLng latlng
     * @param radius for nearbysearch
     * @param type   of search
     * @return MutableLiveData
     */
    public MutableLiveData<ArrayList<Restaurant>> getAllRestaurants(LatLng latLng, String radius, String type) {
        return this.mRepositoryRestaurantList.configureRetrofitCall (latLng, radius, type);
    }

    /**
     * retrofit call to get detail restaurant
     *
     * @param placeId placeId
     * @return MutableLiveData
     */
    public MutableLiveData<DetailRestaurant> getDetailRestaurant(String placeId) {
        return this.mRepositoryRestaurantList.configureDetailRestaurant (placeId);
    }
}
