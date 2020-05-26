package abbesolo.com.go4Lunch.ui.adapter;


import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.models.Workers;
import butterknife.BindView;
import butterknife.ButterKnife;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.ui.adapter. All rights reserved.
//
public class DetailWorkerAdapter extends RecyclerView.Adapter<DetailWorkerAdapter.DetailViewHolder> {

    //FIELD
    private List<Workers> mWorkers;

    //construtor
    public DetailWorkerAdapter(List<Workers> workers) {
        mWorkers = workers;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.item_worker, parent, false);
        return new DetailViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Workers workers = mWorkers.get (position);
        Resources resources = holder.itemView.getResources ();

        String text = workers.getName () + resources.getString (R.string.is_joining);
        holder.mTextView.setText (text);

        Glide.with (holder.mImageView.getContext ())
                .load (workers.getAvatarUrl ())
                .error (R.drawable.pic_logo_restaurant_400x400)
                .apply (RequestOptions.circleCropTransform ())
                .into (holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return ( mWorkers != null ) ? mWorkers.size () : 0;
    }

    //ViewHolder
    class DetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.worker_avatar)
        ImageView mImageView;
        @BindView(R.id.worker_name)
        TextView mTextView;

        DetailViewHolder(@NonNull View itemView) {
            super (itemView);
            ButterKnife.bind (this, itemView);
        }
    }
}
