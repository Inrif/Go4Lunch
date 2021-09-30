package abbesolo.com.go4Lunch.firebase;


import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.Objects;

import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.models.RestaurantFavoris;
import timber.log.Timber;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.firebase. All rights reserved.
//
public class RepositoryFirebase {

    /**
     * create a query for workers BDD
     *
     * @param mWorkers list of workers
     * @return query for Firebase recyclerView options
     */
    public static Query getQueryWorkers(List<abbesolo.com.go4Lunch.models.Users> mWorkers) {
        Query query = UsersHelper.getWorkersCollection ().orderBy ("restaurantName", Query.Direction.DESCENDING);
        query.get ()
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull (task.getResult ())) {
                            abbesolo.com.go4Lunch.models.Users w = document.toObject (abbesolo.com.go4Lunch.models.Users.class);
                            mWorkers.add (w);
                            Timber.d (document.getId () + " => " + document.getData ());
                        }
                    } else {
                        Timber.w (String.valueOf (R.string.error_query), Objects.requireNonNull (task.getException ()).getMessage ());
                    }
                });
        return query;
    }

    /**
     * create a query for favorite restaurant BDD
     *
     * @param favorises list of restaurant
     * @param user      user logged
     * @return query for firebase RV
     */
    public static Query getQueryFavoritesRestaurant(List<RestaurantFavoris> favorises, String user) {
        Query query = RestaurantsFavorisHelper.getAllRestaurantsFromWorkers (user);
        query.get ()
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull (task.getResult ())) {
                            RestaurantFavoris restaurantFavoris = document.toObject (RestaurantFavoris.class);

                            favorises.add (restaurantFavoris);
                        }
                    } else {
                        Timber.w (String.valueOf (R.string.error_query), Objects.requireNonNull (task.getException ()).getMessage ());
                    }
                });
        return query;
    }


}
