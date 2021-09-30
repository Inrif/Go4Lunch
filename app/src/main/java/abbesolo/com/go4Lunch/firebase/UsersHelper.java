package abbesolo.com.go4Lunch.firebase;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.firebase. All rights reserved.
//
public class UsersHelper {

    //FIELD
    private static final String COLLECTION_NAME = "workers";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getWorkersCollection() {
        return FirebaseFirestore.getInstance ().collection (COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<Void> createWorker(String name, String avatar, String resto, String placeId, String uid) {
        abbesolo.com.go4Lunch.models.Users workerToCreate = new abbesolo.com.go4Lunch.models.Users (name, avatar, resto, placeId, uid);
        return UsersHelper.getWorkersCollection ().document (uid).set (workerToCreate);
    }

    // -- GET ALL Users --
    public static Query getAllWorkers() {
        return UsersHelper.getWorkersCollection ();
    }

    // --- GET ---

    public static Task<com.google.firebase.firestore.DocumentSnapshot> getWorker(String uid) {
        return UsersHelper.getWorkersCollection ().document (uid).get ();
    }


    // --- UPDATE ---
    public static void updateRestaurantChoice(String uid, String restoName, String placeId) {

        Map<String, Object> data = new HashMap<> ();
        data.put ("placeId", placeId);
        data.put ("restaurantName", restoName);
        UsersHelper.getWorkersCollection ().document (uid).update (data);
    }
}
