package abbesolo.com.go4Lunch.utils;

import android.view.View;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import abbesolo.com.go4Lunch.models.Restaurant;
import abbesolo.com.go4Lunch.pojos.RestaurantsResult;
import abbesolo.com.go4Lunch.pojos.Result;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.utils. All rights reserved.
//
public abstract class Utils {
    //Show Snack Bar with a message
    public static void showSnackBar(View view, String message) {
        Snackbar.make (view, message, Snackbar.LENGTH_SHORT).show ();
    }

    /**
     * Create an arraylist of restaurant with the json result
     *
     * @param restaurantsResult json result
     * @return arrayList of restaurant
     */
    public static ArrayList<Restaurant> mapRestaurantResultToRestaurant(RestaurantsResult restaurantsResult) {

        ArrayList<Restaurant> resto = new ArrayList<> ();

        for (int i = 0; i < restaurantsResult.getResults ().size (); i++) {
            Result restaurantFirst = restaurantsResult.getResults ().get (i);

            Boolean openNow;
            if (restaurantFirst.getOpeningHours () == null) {
                openNow = false;
            } else {
                openNow = restaurantFirst.getOpeningHours ().getOpenNow ();
            }

            String photo;
            if (restaurantFirst.getPhotos () == null) {
                photo = "";
            } else {
                photo = restaurantFirst.getPhotos ().get (0).getPhotoReference ();
            }
            Restaurant r = new Restaurant (
                    restaurantFirst.getGeometry ().getLocation (),
                    restaurantFirst.getName (),
                    restaurantFirst.getVicinity (),
                    restaurantFirst.getPlaceId (),
                    openNow,
                    photo,
                    0,
                    0,
                    restaurantFirst.getRating ()
            );
            resto.add (r);
        }
        return resto;
    }

    /**
     * create method to attribute stars
     *
     * @param rating restaurant rating
     * @return int to attribute stars
     */
    public static int starsAccordingToRating(double rating) {
        if (rating == 0) {
            return 0;
        } else if (rating > 0 && rating <= 2) {
            return 1;
        } else if (rating > 2 && rating < 3.7) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * update star with rating
     *
     * @param rating rating
     * @param s1     star 1
     * @param s2     star 2
     * @param s3     star 3
     */
    public static void starsView(int rating, ImageView s1, ImageView s2, ImageView s3) {
        switch (rating) {
            case 0:
                s1.setVisibility (View.GONE);
                s2.setVisibility (View.GONE);
                s3.setVisibility (View.GONE);
                break;
            case 1:
                s1.setVisibility (View.VISIBLE);
                s2.setVisibility (View.GONE);
                s3.setVisibility (View.GONE);
                break;
            case 2:
                s1.setVisibility (View.VISIBLE);
                s2.setVisibility (View.VISIBLE);
                s3.setVisibility (View.GONE);
                break;
            case 3:
                s1.setVisibility (View.VISIBLE);
                s2.setVisibility (View.VISIBLE);
                s3.setVisibility (View.VISIBLE);
                break;
        }
    }

    /**
     * set worker choice on restaurant list
     *
     * @param resto   restaurant list
     * @param workers workers list
     * @return arrayList
     */
    public static ArrayList<Restaurant> getChoicedRestaurants(List<Restaurant> resto, ArrayList<abbesolo.com.go4Lunch.models.Users> workers) {
        ArrayList<Restaurant> restaurants = new ArrayList<> ();
        restaurants.clear ();
        for (Restaurant r : resto) {
            int worker = 0;
            for (abbesolo.com.go4Lunch.models.Users w : workers) {
                if (r.getPlaceId ().equalsIgnoreCase (w.getPlaceId ())) {
                    worker++;
                    r.setChoice (true);
                    r.setWorkers (worker);
                }
            }
            restaurants.add (r);
        }
        return restaurants;
    }

    /**
     * filter restaurant with rating stars
     *
     * @param restaurants list
     * @param filter      filter
     * @return arrayList of restaurant filtered
     */
    public static ArrayList<Restaurant> filterRestaurantList(ArrayList<Restaurant> restaurants, int filter) {
        ArrayList<Restaurant> restaurantArrayList = new ArrayList<> ();
        for (Restaurant restaurant : restaurants) {
            int rating = Utils.starsAccordingToRating (restaurant.getRating ());
            if (rating == filter) {
                restaurantArrayList.add (restaurant);
            }
        }
        return restaurantArrayList;
    }
}
