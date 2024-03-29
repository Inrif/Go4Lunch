package abbesolo.com.go4Lunch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abbesolo.com.go4Lunch.models.Restaurant;
import abbesolo.com.go4Lunch.utils.Utils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

//
// Created by HOUNSA Romuald on 2020-05-14.
// Copyright (c) 2020 abbesolo.com.go4Lunch. All rights reserved.
//
public class UtilsTest {

    private static final List<Restaurant> mRestaurantsGenerate = Arrays.asList (
            new Restaurant (null, "le Zinc", "2 boulevard poissonnière",
                    "000", false, null, 0, 0, 2.5),
            new Restaurant (null, "La cigale", "21 place Graslin",
                    "021", true, null, 0, 2, 4.5),
            new Restaurant (null, "mac donald", "place graslin",
                    "00025", false, null, 0, 0, 1.5),
            new Restaurant (null, "Grillpizz", "place graslin",
                    "00028", false, null, 0, 0, 3)
    );
    private static final List<abbesolo.com.go4Lunch.models.Users> sMUsersGenerate = Arrays.asList (
            new abbesolo.com.go4Lunch.models.Users ("Carole", null, "La cigale", "021",

                    "QWRfgt"),
            new abbesolo.com.go4Lunch.models.Users ("Cyril", null, "", "", "mouyg"),
            new abbesolo.com.go4Lunch.models.Users ("Fabrice", null, "le Zinc", "000", "QXCZS"),
            new abbesolo.com.go4Lunch.models.Users ("Virgile", null, "La cigale", "021", "WDCXZAS")
    );
    private final ArrayList<Restaurant> mRestaurants = generatorOfRestaurant ();
    private final ArrayList<abbesolo.com.go4Lunch.models.Users> mWorkers = generatorOfWorkers ();

    private static ArrayList<Restaurant> generatorOfRestaurant() {
        return new ArrayList<> (mRestaurantsGenerate);
    }

    private static ArrayList<abbesolo.com.go4Lunch.models.Users> generatorOfWorkers() {
        return new ArrayList<> (sMUsersGenerate);
    }

    @Test
    public void getChoiceRestaurantWithSuccess() {
        //create list restaurant
        ArrayList<Restaurant> restaurantArrayList = Utils.getChoicedRestaurants (mRestaurants, mWorkers);
        //assert first restaurant is choice
        assertTrue (restaurantArrayList.get (0).isChoice ());
        //assert first restaurant has a worker
        assertEquals (1, restaurantArrayList.get (0).getWorkers ());
        //assert third restaurant haven't worker
        assertEquals (0, restaurantArrayList.get (2).getWorkers ());
        //assert second restaurant has 2 workers
        assertEquals (2, restaurantArrayList.get (1).getWorkers ());
    }

    @Test
    public void getFilterRestaurantWithSuccess() {
        //create list restaurant
        ArrayList<Restaurant> restaurantArrayList = Utils.getChoicedRestaurants (mRestaurants, mWorkers);
        //assert there is 2 restaurants with 3 stars
        assertEquals (2, Utils.filterRestaurantList (restaurantArrayList, 2).size ());
        //assert there is no restaurant with 0 stars
        assertEquals (0, Utils.filterRestaurantList (restaurantArrayList, 0).size ());
    }

}

