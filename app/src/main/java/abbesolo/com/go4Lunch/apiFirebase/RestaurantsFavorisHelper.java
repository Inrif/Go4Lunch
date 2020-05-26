package abbesolo.com.go4Lunch.apiFirebase;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import abbesolo.com.go4Lunch.models.RestaurantFavoris;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.apiFirebase. All rights reserved.
//
public class RestaurantsFavorisHelper {

    ///FIELD
    private static final String COLLECTION_NAME = "restaurants_favoris";

    // --- CREATE ---

    public static Task<DocumentReference> createFavoriteRestaurant(String user, String uid, String name, String placeId,
                                                                   String address, String photoReference, double rating) {
        RestaurantFavoris restoToCreate = new RestaurantFavoris (uid, name, placeId, address, photoReference, rating);

        //Store RestaurantFavoris to Firestore
        return WorkersHelper.getWorkersCollection ()
                .document (user)
                .collection (COLLECTION_NAME)
                .add (restoToCreate);
    }

    // --- GET ---

    public static Query getAllRestaurantsFromWorkers(String name) {
        return WorkersHelper.getWorkersCollection ()
                .document (name)
                .collection (COLLECTION_NAME);
    }

    // --- DELETE ---

    public static void deleteRestaurant(String user, String uid) {
        WorkersHelper.getWorkersCollection ()
                .document (user)
                .collection (COLLECTION_NAME)
                .document (uid)
                .delete ();
    }
}
