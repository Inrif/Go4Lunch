package abbesolo.com.go4Lunch.base;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public abstract class BaseActivity extends AppCompatActivity {

    // fields
    protected com.google.firebase.auth.FirebaseAuth mFirebaseAuth;

    // Methods
    public abstract int getActivityLayout();

    @androidx.annotation.Nullable
    protected abstract androidx.appcompat.widget.Toolbar getToolbar();

    // --------------------
    // Activity
    // --------------------

    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.setContentView (this.getActivityLayout ());
        butterknife.ButterKnife.bind (this); //Configure Butterknife
        this.configureFirebaseAuth (); // configure firebase
    }

    // --------------------
    // UI
    // --------------------

    protected void configureToolBar(String text) {
        // If ToolBar exists
        if (this.getToolbar () != null) {
            getToolbar ().setTitle (text);
            setSupportActionBar (this.getToolbar ());
        }
    }

    /**
     * Configures the {@link FirebaseAuth}
     */
    private void configureFirebaseAuth() {
        this.mFirebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance ();
    }

    // --------------------
    // UTILS
    // --------------------

    @androidx.annotation.Nullable
    protected com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser ();
    }

    // --------------------
    // ERROR HANDLER
    //  --------------------

    protected com.google.android.gms.tasks.OnFailureListener onFailureListener() {
        return e -> android.widget.Toast.makeText (getApplicationContext (), getString (abbesolo.com.go4Lunch.R.string.error_unknown_error), android.widget.Toast.LENGTH_LONG).show ();
    }
}

