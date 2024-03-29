package abbesolo.com.go4Lunch.googleMap;


import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abbesolo.com.go4Lunch.BuildConfig;
import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.models.DetailRestaurant;
import abbesolo.com.go4Lunch.models.Restaurant;
import abbesolo.com.go4Lunch.pojos.Detail;
import abbesolo.com.go4Lunch.pojos.DetailsResult;
import abbesolo.com.go4Lunch.pojos.RestaurantsResult;
import abbesolo.com.go4Lunch.utils.Utils;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;


//
// Created by Hounsa Romuald on 2020-03-17.
// Copyright (c) 2020 abbesolo.com.go4Lunch.googleMap. All rights reserved.
//
@SuppressWarnings("unused")
public class RepositoryRestaurantList implements NearbyPlaces {


    //FIELDS
    private MutableLiveData<ArrayList<Restaurant>> mRestaurantList;
    private MutableLiveData<DetailRestaurant> mDetailRestaurantLiveData;
    private Disposable disposable;

    //Implementation interface method
    @Override
    public MutableLiveData<ArrayList<Restaurant>> configureRetrofitCall(LatLng latLng, String radius, String type) {

        mRestaurantList = new MutableLiveData<> ();

        Map<String, String> parameters = new HashMap<> ();
        parameters.put ("location", latLng.latitude + "," + latLng.longitude);
        parameters.put ("radius", radius);
        parameters.put ("type", type);
        parameters.put ("key", BuildConfig.google_maps_key);


        this.disposable = PlaceStream.streamGetNearByRestaurant (parameters)
                .subscribeWith (new DisposableObserver<RestaurantsResult> () {
                    @Override
                    public void onNext(RestaurantsResult restaurantsResult) {
                        if (restaurantsResult != null) {
                            mRestaurantList.setValue (Utils.mapRestaurantResultToRestaurant (restaurantsResult));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e (String.valueOf (R.string.error_stream), e.getMessage ());
                    }

                    @Override
                    public void onComplete() {
                        Timber.i (String.valueOf (R.string.on_Complete_message));
                    }
                });

        return this.mRestaurantList;
    }

    @Override
    public MutableLiveData<DetailRestaurant> configureDetailRestaurant(String placeId) {

        mDetailRestaurantLiveData = new MutableLiveData<> ();

        Map<String, String> parameters = new HashMap<> ();

        parameters.put ("placeid", placeId);
        parameters.put ("key", BuildConfig.google_maps_key);


        this.disposable = PlaceStream.streamGetDetailRestaurant (parameters)
                .subscribeWith (new DisposableObserver<Detail> () {
                    @Override
                    public void onNext(Detail detail) {
                        if (detail != null) {
                            DetailsResult detailsResult = detail.getResult ();
                            if (detailsResult != null) {
                                String photo;
                                if (detailsResult.getPhotos () == null) {
                                    photo = "";
                                } else {
                                    photo = detailsResult.getPhotos ().get (0).getPhotoReference ();
                                }

                                DetailRestaurant restaurant = new DetailRestaurant (
                                        detailsResult.getFormattedAddress (),
                                        detailsResult.getFormattedPhoneNumber (),
                                        detailsResult.getName (),
                                        photo,
                                        ( detailsResult.getRating () != null ) ? detailsResult.getRating () : 0,
                                        detailsResult.getWebsite ()
                                );
                                mDetailRestaurantLiveData.setValue (restaurant);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e (String.valueOf (R.string.error_stream), e.getMessage ());
                    }

                    @Override
                    public void onComplete() {
                        Timber.i (String.valueOf (R.string.on_Complete_message));
                    }
                });

        return mDetailRestaurantLiveData;
    }


}
