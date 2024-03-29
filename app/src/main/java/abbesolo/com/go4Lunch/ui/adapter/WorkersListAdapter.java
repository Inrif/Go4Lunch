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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.R.color;
import butterknife.BindView;
import butterknife.ButterKnife;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.ui.adapter. All rights reserved.
//
public class WorkersListAdapter extends FirestoreRecyclerAdapter<abbesolo.com.go4Lunch.models.Users, WorkersListAdapter.WorkersItemViewholder> {


    //FIELD
    private final workerClickListener mWorkerClickListener;

    //constructor
    public WorkersListAdapter(@NonNull FirestoreRecyclerOptions<abbesolo.com.go4Lunch.models.Users> options, workerClickListener mWorkerListener) {
        super (options);
        this.mWorkerClickListener = mWorkerListener;
    }

    @NonNull
    @Override
    public WorkersItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.item_worker, parent, false);
        return new WorkersItemViewholder (view, mWorkerClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull WorkersItemViewholder holder, int i, @NonNull abbesolo.com.go4Lunch.models.Users users) {
        Resources resource = holder.itemView.getContext ().getResources ();
        String text;

        if (users.getRestaurantName () != null) {
            if (!users.getRestaurantName ().trim ().equals ("")) {
                text = users.getName () + " " + resource.getString (R.string.is_eating_at) + users.getRestaurantName ();
                holder.mTextView.setTextColor (resource.getColor (color.secondary_text));
            } else {
                text = users.getName () + " " + resource.getString (R.string.hasn_t_decided);
                holder.mTextView.setTextColor (resource.getColor (color.color_workers));
            }
            holder.mTextView.setText (text);

            Glide.with (holder.mImageView.getContext ())
                    .load (users.getAvatarUrl ())
                    .error (R.drawable.pic_logo_restaurant_400x400)
                    .apply (RequestOptions.circleCropTransform ())
                    .into (holder.mImageView);
        }
    }

    //interface to listen the click
    public interface workerClickListener {
        void onClickItemWorker(int position);
    }

    //ViewHolder
    public class WorkersItemViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.worker_avatar)
        ImageView mImageView;
        @BindView(R.id.worker_name)
        TextView mTextView;

        workerClickListener mWorkerClickListener;

        WorkersItemViewholder(@NonNull View itemView, workerClickListener listener) {
            super (itemView);
            this.mWorkerClickListener = listener;
            ButterKnife.bind (this, itemView);
            itemView.setOnClickListener (this);
        }

        @Override
        public void onClick(View v) {
            mWorkerClickListener.onClickItemWorker (getAdapterPosition ());
        }
    }
}
