package abbesolo.com.go4Lunch.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.models.Restaurant;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.ui.adapter. All rights reserved.
//
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantItemViewHolder> {


    //FILEDS
    private ArrayList<Restaurant> mRestaurantList;
    private RequestManager glide;
    private onClickRestaurantItemListener mOnClickRestaurantitemListener;

    //constructor
    public RestaurantListAdapter(ArrayList<Restaurant> restaurantList,
                                 RequestManager glide,
                                 onClickRestaurantItemListener onClickRestaurantitemListener) {

        mRestaurantList = restaurantList;
        this.glide = glide;
        this.mOnClickRestaurantitemListener = onClickRestaurantitemListener;
    }

    @NonNull
    @Override
    public RestaurantItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.item_restaurant, parent, false);
        return new RestaurantItemViewHolder (view, mOnClickRestaurantitemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemViewHolder holder, int position) {
        holder.updateWithDetailRestaurant (this.mRestaurantList.get (position), this.glide);
    }

    @Override
    public int getItemCount() {
        return ( mRestaurantList != null ) ? mRestaurantList.size () : 0;
    }

    //interface to listen the click
    public interface onClickRestaurantItemListener {
        void onClickRestaurantItem(int position);
    }
}
