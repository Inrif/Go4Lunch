package abbesolo.com.go4Lunch.apiFirebase;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import abbesolo.com.go4Lunch.models.Workers;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.apiFirebase. All rights reserved.
//
public class WorkersHelper {

    //FIELD
    private static final String COLLECTION_NAME = "workers";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getWorkersCollection() {
        return FirebaseFirestore.getInstance ().collection (COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<Void> createWorker(String name, String avatar, String resto, String placeId, String uid) {
        Workers workerToCreate = new Workers (name, avatar, resto, placeId, uid);
        return WorkersHelper.getWorkersCollection ().document (uid).set (workerToCreate);
    }

    // -- GET ALL Workers --
    public static Query getAllWorkers() {
        return WorkersHelper.getWorkersCollection ();
    }

    // --- UPDATE ---
    public static void updateRestaurantChoice(String uid, String restoName, String placeId) {

        Map<String, Object> data = new HashMap<> ();
        data.put ("placeId", placeId);
        data.put ("restaurantName", restoName);
        WorkersHelper.getWorkersCollection ().document (uid).update (data);
    }
}
