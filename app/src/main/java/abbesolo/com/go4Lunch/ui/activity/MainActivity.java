package abbesolo.com.go4Lunch.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import abbesolo.com.go4Lunch.BuildConfig;
import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.apiFirebase.WorkersHelper;
import abbesolo.com.go4Lunch.base.BaseActivity;
import abbesolo.com.go4Lunch.settings.activity.SettingsActivity;
import abbesolo.com.go4Lunch.ui.fragments.MapFragment;
import abbesolo.com.go4Lunch.ui.fragments.RestaurantListFragment;
import abbesolo.com.go4Lunch.ui.fragments.WorkersFragment;
import abbesolo.com.go4Lunch.utils.Utils;
import butterknife.BindView;

import static androidx.core.view.GravityCompat.START;

public class MainActivity extends BaseActivity {


    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    @BindView(R.id.nav_view)
    BottomNavigationView navView;
    @BindView(abbesolo.com.go4Lunch.R.id.drawer_layout)
    androidx.drawerlayout.widget.DrawerLayout mDrawerLayout;
    @BindView(abbesolo.com.go4Lunch.R.id.toolbar)
    androidx.appcompat.widget.Toolbar mToolbar;
    @BindView(R.id.drawer_navigation)
    NavigationView mNavigationView;
    //FIELDS
    private androidx.fragment.app.Fragment mFragment;
    private com.google.firebase.auth.FirebaseUser user;

    // base activity method
    @Override
    public int getActivityLayout() {
        return abbesolo.com.go4Lunch.R.layout.activity_main;
    }

    @androidx.annotation.Nullable
    @Override
    protected androidx.appcompat.widget.Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        if (mFragment == null) {
            mFragment = new MapFragment ();
        }

        user = this.getCurrentUser ();
        configureFragment (mFragment);
        this.configureToolBar (getString (R.string.I_m_hungry));

        navView.setOnNavigationItemSelectedListener (this::updateMainFragment);

        drawerLayoutConfiguration ();
        configureNavigationHeader ();

        // Initialize the SDK for autocomplete
        Places.initialize (getApplicationContext (), BuildConfig.google_maps_key);

    }

    /**
     * configure navigation drawer header
     */
    private void configureNavigationHeader() {
        mNavigationView.setNavigationItemSelectedListener (this::updateMainFragment);
        this.updateNavigationHeader ();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        // fragment open first after permission granted
        Fragment fragment = getSupportFragmentManager ().findFragmentById (R.id.map_Fragment);
        java.util.Objects.requireNonNull (fragment).onActivityResult (requestCode, resultCode, data);

        //  go to detail page when we click on result search
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent (data);
                Intent intent = new android.content.Intent (this, RestaurantDetail.class);
                intent.putExtra ("placeId", place.getId ());
                startActivity (intent);
            } else {
                android.widget.Toast.makeText (getApplicationContext (), getResources ().getString (R.string.error_query), android.widget.Toast.LENGTH_LONG).show ();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Creates a MenuInflater to add the menu xml file into the Toolbar
        getMenuInflater ().inflate (R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId () == R.id.toolbar_search) {
            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            List<Place.Field> fields = Arrays.asList (Place.Field.ID, Place.Field.NAME);

            // Define the region
            RectangularBounds bounds = RectangularBounds.newInstance (
                    new LatLng (6.37371, 2.394234),
                    new LatLng (6.38480, 2.385344));


            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder (
                    AutocompleteActivityMode.OVERLAY, fields)
                    .setLocationBias (bounds)
                    .setTypeFilter (TypeFilter.ESTABLISHMENT)
                    .build (this);

            startActivityForResult (intent, AUTOCOMPLETE_REQUEST_CODE);

            return true;
        }
        return super.onOptionsItemSelected (item);
    }

    /**
     * update main fragment when item is clicked
     *
     * @param menuItem item to click on
     * @return new fragment
     */
    private Boolean updateMainFragment(MenuItem menuItem) {
        switch (menuItem.getItemId ()) {
            case R.id.navigation_map:
                this.mFragment = new MapFragment ();
                configureFragment (mFragment);
                mToolbar.setTitle (getResources ().getString (R.string.I_m_hungry));
                break;
            case R.id.navigation_list:
                this.mFragment = new RestaurantListFragment ();
                configureFragment (mFragment);
                mToolbar.setTitle (getResources ().getString (R.string.I_m_hungry));
                break;
            case R.id.navigation_workers:
                this.mFragment = new WorkersFragment ();
                configureFragment (mFragment);
                mToolbar.setTitle (getString (R.string.workers_toolbar));
                break;
            case R.id.logout:
                this.signOutCurrentUser ();
                break;
            case R.id.nav_lunch:
                this.showMyRestaurantChoice ();
                break;
            case R.id.nav_favorite:
                this.showMyFavoriteRestaurant ();
                break;
            case R.id.nav_settings:
                this.startActivitySettings ();
        }
        // Closes the DrawerNavigationView when the user click on an item
        if (this.mDrawerLayout.isDrawerOpen (START)) {
            this.mDrawerLayout.closeDrawer (START);
        }
        return true;
    }

    /**
     * create new fragment
     *
     * @param fragment to configure
     */
    private void configureFragment(Fragment fragment) {
        getSupportFragmentManager ().beginTransaction ()
                .replace (R.id.fragment_main, fragment)
                .commit ();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen (START)) {
            mDrawerLayout.closeDrawer (START);
        } else {
            super.onBackPressed ();
        }
    }

    /**
     * configuration of the drawer layout
     */
    private void drawerLayoutConfiguration() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        this.mDrawerLayout.addDrawerListener (toggle);
        toggle.syncState ();
    }

    /**
     * Update header with user information
     */
    private void updateNavigationHeader() {
        final View headerNav = mNavigationView.getHeaderView (0);

        //XML id for update data
        ImageView imageViewNav = headerNav.findViewById (R.id.image_navDrawer);
        TextView textViewNavName = headerNav.findViewById (R.id.name_text);
        TextView textViewNavMail = headerNav.findViewById (R.id.mail_text);

        if (user != null) {
            // ImageView: User image
            if (user.getPhotoUrl () != null) {
                Glide.with (this)
                        .load (user.getPhotoUrl ())
                        .circleCrop ()
                        .into (imageViewNav);
            }

            // TextView: Username and email
            final String username = TextUtils.isEmpty (user.getDisplayName ()) ? getString (R.string.no_name_found) :
                    user.getDisplayName ();

            final String email = TextUtils.isEmpty (user.getEmail ()) ? getString (R.string.no_mail_found) :
                    user.getEmail ();

            textViewNavName.setText (username);
            textViewNavMail.setText (email);
        }
    }

    /**
     * Signs out the current user of Firebase
     */
    private void signOutCurrentUser() {
        Utils.showSnackBar (this.mDrawerLayout, "sign out");
        if (user != null) {
            com.google.firebase.auth.FirebaseAuth.getInstance ().signOut ();
            startAuthActivity ();
            finishAffinity ();
        }
    }

    // Intent used for navigation item
    private void startAuthActivity() {
        android.content.Intent intent = new android.content.Intent (this, ConnectionActivity.class);
        startActivity (intent);
    }

    private void showMyFavoriteRestaurant() {
        android.content.Intent intent = new android.content.Intent (this, FavoritesRestaurant.class);
        startActivity (intent);
    }

    private void startActivitySettings() {
        android.content.Intent intent = new android.content.Intent (this, SettingsActivity.class);
        startActivity (intent);
    }

    /**
     * get a user query to show if the user has chosen a restaurant and redirect if able
     */
    private void showMyRestaurantChoice() {

        android.util.Log.e ("getCurrentUser", "not Nul");

        Query query = WorkersHelper.getAllWorkers ().whereEqualTo ("name",
                Objects.requireNonNull (getCurrentUser ()).getDisplayName ());

        query.get ().addOnCompleteListener (task -> {
            if (task.isSuccessful ()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull (task.getResult ())) {
                    if (!Objects.equals (document.get ("placeId"), "")) {
                        Intent intent = new Intent (this.getBaseContext (), RestaurantDetail.class);
                        intent.putExtra ("placeId", Objects.requireNonNull (document.get ("placeId")).toString ());
                        startActivity (intent);
                    } else {
                        Utils.showSnackBar (this.mDrawerLayout, getResources ().getString (R.string.no_choice_restaurant_workers));
                    }
                }
            }
        });
    }
}





















