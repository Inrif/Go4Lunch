package abbesolo.com.go4Lunch.ui.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Objects;

import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.models.Restaurant;
import abbesolo.com.go4Lunch.ui.activity.RestaurantDetail;
import abbesolo.com.go4Lunch.ui.adapter.RestaurantListAdapter;
import abbesolo.com.go4Lunch.ui.viewModels.MapViewModel;
import abbesolo.com.go4Lunch.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment implements RestaurantListAdapter.onClickRestaurantItemListener {
    //constant
    private static final String PREF_RADIUS = "radius_key";
    private static final String PREF_TYPE = "type_key";
    private final CollectionReference workersRef = abbesolo.com.go4Lunch.firebase.UsersHelper.getWorkersCollection ();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private final ArrayList<Restaurant> mRestaurantsToDisplay = new ArrayList<> ();
    private final ListenerRegistration mListenerRegistration = null;
    //    @BindView(R.id.star_1)
//    TextView star_1;
//    @BindView(R.id.star_2)
//    TextView star_2;
//    @BindView(R.id.star_3)
//    TextView star_3;
//    @BindView(R.id.no_star)
//    TextView no_star;
//    @BindView(R.id.no_filter)
//    TextView no_filter;
//    @BindView(R.id.text_no_restaurant)
//    TextView no_restaurant;
    //FIELD
    private ArrayList<Restaurant> mRestaurants;
    private MapViewModel mMapViewModel;
    private LatLng currentPosition;
    private FusedLocationProviderClient mFusedLocationClient;
    private ArrayList<abbesolo.com.go4Lunch.models.Users> mUsersArrayList;

    //constructor
    public RestaurantListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        // Location Services
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient (Objects.requireNonNull (getContext ()));

        mMapViewModel = ViewModelProviders.of (Objects.requireNonNull (this.getActivity ())).get (MapViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_restaurant_list, container, false);

        ButterKnife.bind (this, view);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (view.getContext ()));

        return view;
    }

//    @OnClick({R.id.no_star,
//            R.id.star_1,
//            R.id.star_3,
//            R.id.star_2,
//            R.id.no_filter})
//    void onClickFilter(View view) {
//        mRestaurantsToDisplay.clear ();
//        switch (view.getId ()) {
//            case R.id.no_star:
//                mRestaurantsToDisplay.addAll (Utils.filterRestaurantList (mRestaurants, 0));
//                setButtonChoiceChange (R.id.no_star);
//                break;
//            case R.id.star_1:
//                mRestaurantsToDisplay.addAll (Utils.filterRestaurantList (mRestaurants, 1));
//                setButtonChoiceChange (R.id.star_1);
//                break;
//            case R.id.star_2:
//                mRestaurantsToDisplay.addAll (Utils.filterRestaurantList (mRestaurants, 2));
//                setButtonChoiceChange (R.id.star_2);
//                break;
//            case R.id.star_3:
//                mRestaurantsToDisplay.addAll (Utils.filterRestaurantList (mRestaurants, 3));
//                setButtonChoiceChange (R.id.star_3);
//                break;
//            case R.id.no_filter:
//                mRestaurantsToDisplay.addAll (mRestaurants);
//                setButtonChoiceChange (R.id.no_filter);
//                break;
//        }
//        if (mRestaurantsToDisplay.isEmpty ()) {
//            no_restaurant.setVisibility (View.VISIBLE);
//            mRecyclerView.setVisibility (View.GONE);
//        } else {
//            Objects.requireNonNull (mRecyclerView.getAdapter ()).notifyDataSetChanged ();
//            no_restaurant.setVisibility (View.GONE);
//            mRecyclerView.setVisibility (View.VISIBLE);
//        }
//    }

    @Override
    public void onStart() {
        super.onStart ();
        initRestaurantList ();
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        if (mListenerRegistration != null) {
            mListenerRegistration.remove ();
        }
    }

    /**
     * create a list of restaurant with settings and current position
     */
    private void initRestaurantList() {
        workersRef.addSnapshotListener ((queryDocumentSnapshots, e) -> {
            mUsersArrayList = new ArrayList<> ();
            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot data : Objects.requireNonNull (queryDocumentSnapshots).getDocuments ()) {
                    if (data.get ("placeId") != null) {
                        abbesolo.com.go4Lunch.models.Users users = data.toObject (abbesolo.com.go4Lunch.models.Users.class);
                        mUsersArrayList.add (users);
                        Timber.i ("snap users List Restaurant : %s", mUsersArrayList.size ());
                    }
                }
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences (Objects.requireNonNull (this.getContext ()));
        if (androidx.core.app.ActivityCompat.checkSelfPermission (getContext (), android.Manifest.permission.ACCESS_FINE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED && androidx.core.app.ActivityCompat.checkSelfPermission (getContext (), android.Manifest.permission.ACCESS_COARSE_LOCATION) != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation ()
                .addOnSuccessListener (Objects.requireNonNull (getActivity ()), location -> {
                    if (location != null) {
                        // get the location phone
                        currentPosition = new LatLng (location.getLatitude (), location.getLongitude ());
                        //observe restaurants data
                        mMapViewModel.getAllRestaurants (currentPosition,
                                sharedPreferences.getString (PREF_RADIUS, ""),
                                sharedPreferences.getString (PREF_TYPE, "restaurant"))
                                .observe (Objects.requireNonNull (this.getActivity ()), this::initListAdapter);

                    }
                });
    }

    /**
     * init adapter for RV
     *
     * @param restaurants list
     */
    private void initListAdapter(ArrayList<Restaurant> restaurants) {
        mRestaurantsToDisplay.addAll (getRestaurantFromJson (restaurants));
        RestaurantListAdapter adapter = new RestaurantListAdapter (mRestaurantsToDisplay, Glide.with (this), this);
        mRecyclerView.setAdapter (adapter);
    }

    /**
     * format restaurant list with distance and workers information choice
     *
     * @param restaurants list
     * @return restaurant list
     */
    private ArrayList<Restaurant> getRestaurantFromJson(ArrayList<Restaurant> restaurants) {
        mRestaurants = Utils.getChoicedRestaurants (restaurants, mUsersArrayList);
        getDistanceFromMyPosition (mRestaurants);
        return mRestaurants;
    }

    @Override
    public void onClickRestaurantItem(int position) {
        Intent intent = new Intent (getContext (), RestaurantDetail.class);
        intent.putExtra ("placeId", mRestaurants.get (position).getPlaceId ());
        intent.putExtra ("restaurantName", mRestaurants.get (position).getName ());
        startActivity (intent);
    }

    /**
     * calculate distance between restaurant and current position
     *
     * @param restaurants list
     */
    private void getDistanceFromMyPosition(ArrayList<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            float[] results = new float[1];
            Location.distanceBetween (currentPosition.latitude, currentPosition.longitude,
                    restaurant.getLocation ().getLat (), restaurant.getLocation ().getLng (), results);
            int distance = ( int ) results[0];
            restaurant.setDistance (distance);
        }
    }

//    private void setButtonChoiceChange(int textView) {
//        switch (textView) {
//            case R.id.no_star:
//                no_star.setTextColor (getResources ().getColor (R.color.colorAccent));
//                star_1.setTextColor (getResources ().getColor (R.color.white_text));
//                star_2.setTextColor (getResources ().getColor (R.color.white_text));
//                star_3.setTextColor (getResources ().getColor (R.color.white_text));
//                no_filter.setTextColor (getResources ().getColor (R.color.white_text));
//                break;
//            case R.id.star_1:
//                star_1.setTextColor (getResources ().getColor (R.color.colorAccent));
//                no_star.setTextColor (getResources ().getColor (R.color.white_text));
//                star_2.setTextColor (getResources ().getColor (R.color.white_text));
//                star_3.setTextColor (getResources ().getColor (R.color.white_text));
//                no_filter.setTextColor (getResources ().getColor (R.color.white_text));
//                break;
//            case R.id.star_2:
//                star_2.setTextColor (getResources ().getColor (R.color.colorAccent));
//                star_1.setTextColor (getResources ().getColor (R.color.white_text));
//                no_star.setTextColor (getResources ().getColor (R.color.white_text));
//                star_3.setTextColor (getResources ().getColor (R.color.white_text));
//                no_filter.setTextColor (getResources ().getColor (R.color.white_text));
//                break;
//            case R.id.star_3:
//                star_3.setTextColor (getResources ().getColor (R.color.colorAccent));
//                star_1.setTextColor (getResources ().getColor (R.color.white_text));
//                star_2.setTextColor (getResources ().getColor (R.color.white_text));
//                no_star.setTextColor (getResources ().getColor (R.color.white_text));
//                no_filter.setTextColor (getResources ().getColor (R.color.white_text));
//                break;
//            case R.id.no_filter:
//                no_filter.setTextColor (getResources ().getColor (R.color.colorAccent));
//                star_1.setTextColor (getResources ().getColor (R.color.white_text));
//                star_2.setTextColor (getResources ().getColor (R.color.white_text));
//                star_3.setTextColor (getResources ().getColor (R.color.white_text));
//                no_star.setTextColor (getResources ().getColor (R.color.white_text));
//                break;
//
//        }
//    }

}
