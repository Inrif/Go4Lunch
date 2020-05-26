package abbesolo.com.go4Lunch.base;


import android.app.Application;

import abbesolo.com.go4Lunch.apiGoogleMap.RestaurantService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.base. All rights reserved.
//
public class App extends Application {
    /**
     * create the retrofit configuration
     *
     * @return RestaurantService interface
     */
    public static RestaurantService retrofitCall() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder ()
                .addInterceptor (new HttpLoggingInterceptor ().setLevel (HttpLoggingInterceptor.Level.BODY))
                .build ();

        Retrofit retrofit = new Retrofit.Builder ()
                .client (okHttpClient)
                .baseUrl ("https://maps.googleapis.com/maps/api/place/")
                .addConverterFactory (GsonConverterFactory.create ())
                .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                .build ();

        return retrofit.create (RestaurantService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate ();
        Timber.plant (new Timber.DebugTree ());
    }
}
