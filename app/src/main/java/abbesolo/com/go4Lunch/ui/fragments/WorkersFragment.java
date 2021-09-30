package abbesolo.com.go4Lunch.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.firebase.RepositoryFirebase;
import abbesolo.com.go4Lunch.ui.activity.RestaurantDetail;
import abbesolo.com.go4Lunch.ui.adapter.WorkersListAdapter;
import abbesolo.com.go4Lunch.utils.Utils;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkersFragment extends Fragment implements WorkersListAdapter.workerClickListener {

    @BindView(R.id.recyclerView_workers)
    RecyclerView mRecyclerView;
    //FIELDS
    //private RecyclerView mRecyclerView;
    private WorkersListAdapter adapter;
    private final List<abbesolo.com.go4Lunch.models.Users> mWorkers = new ArrayList<> ();

    //constructor
    public WorkersFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance ();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder ()
                .build ();
        firestore.setFirestoreSettings (settings);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_workers, container, false);

        mRecyclerView = view.findViewById (R.id.recyclerView_workers);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (view.getContext ()));

        initListAdapter ();
        return view;
    }

    /**
     * init adapter
     */
    private void initListAdapter() {
        Query query = RepositoryFirebase.getQueryWorkers (mWorkers);
        FirestoreRecyclerOptions<abbesolo.com.go4Lunch.models.Users> options = new FirestoreRecyclerOptions.Builder<abbesolo.com.go4Lunch.models.Users> ()
                .setQuery (query, abbesolo.com.go4Lunch.models.Users.class)
                .build ();

        adapter = new WorkersListAdapter (options, this);
        mRecyclerView.setAdapter (adapter);
    }

    @Override
    public void onStart() {
        super.onStart ();
        adapter.startListening ();
    }

    @Override
    public void onStop() {
        super.onStop ();
        adapter.stopListening ();
    }

    @Override
    public void onClickItemWorker(int position) {
        if (mWorkers.get (position).getPlaceId ().trim ().equals ("")) {
            Utils.showSnackBar (this.mRecyclerView, getString (R.string.no_choice_restaurant_workers));
        } else {
            Intent intent = new Intent (getContext (), RestaurantDetail.class);
            intent.putExtra ("placeId", mWorkers.get (position).getPlaceId ());
            intent.putExtra ("restaurantName", mWorkers.get (position).getRestaurantName ());
            startActivity (intent);
        }
    }
}
