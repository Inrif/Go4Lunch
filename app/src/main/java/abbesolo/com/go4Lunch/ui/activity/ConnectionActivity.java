package abbesolo.com.go4Lunch.ui.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.Objects;

import abbesolo.com.go4Lunch.R;
import abbesolo.com.go4Lunch.base.BaseActivity;
import abbesolo.com.go4Lunch.utils.Utils;
import butterknife.BindView;
import butterknife.OnClick;

public class ConnectionActivity extends BaseActivity {

    //FOR DATA
    private static final int RC_SIGN_IN = 123;
    @BindView(R.id.layout_main)
    ConstraintLayout mConstraintLayout;

    // base activity method
    @Override
    public int getActivityLayout() {
        return R.layout.activity_connection;
    }

    @Nullable
    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume ();
        // Checks if user is signed in (non-null)
        if (FirebaseAuth.getInstance ().getCurrentUser () != null) {
            this.startMainActivity ();
        }
    }

    @OnClick({R.id.main_activity_google_login_button,
            R.id.main_activity_facebook_login_button,
            R.id.main_activity_twitter_login_button,
            R.id.main_activity_email_login_button})
    public void onClickLogginButton(View view) {
        switch (view.getId ()) {
            case R.id.main_activity_google_login_button:
                this.startSignInActivityGoogle ();
                break;
            case R.id.main_activity_facebook_login_button:
                this.startSignInActivityFacebook ();
                break;
            case R.id.main_activity_twitter_login_button:
                this.startSignInActivityTwitter ();
                break;
            case R.id.main_activity_email_login_button:
                this.startSignInActivityMail ();
                break;
        }
    }

    // --------------------
    // Authentications
    // --------------------

    private void startSignInActivityGoogle() {
        startActivityForResult (
                AuthUI.getInstance ()
                        .createSignInIntentBuilder ()
                        .setAvailableProviders (
                                Collections.singletonList (new AuthUI.IdpConfig.GoogleBuilder ().build ()))
                        .setIsSmartLockEnabled (false, true)
                        .build (),
                RC_SIGN_IN);
    }

    private void startSignInActivityMail() {
        startActivityForResult (
                AuthUI.getInstance ()
                        .createSignInIntentBuilder ()
                        .setAvailableProviders (
                                Collections.singletonList (new AuthUI.IdpConfig.EmailBuilder ().build ()))
                        .setIsSmartLockEnabled (false, true)
                        .build (),
                RC_SIGN_IN);
    }

    private void startSignInActivityFacebook() {
        startActivityForResult (
                AuthUI.getInstance ()
                        .createSignInIntentBuilder ()
                        .setAvailableProviders (
                                Collections.singletonList (new AuthUI.IdpConfig.FacebookBuilder ().build ()))
                        .setIsSmartLockEnabled (false, true)
                        .build (),
                RC_SIGN_IN);
    }

    private void startSignInActivityTwitter() {
        startActivityForResult (
                AuthUI.getInstance ()
                        .createSignInIntentBuilder ()
                        .setAvailableProviders (
                                Collections.singletonList (new AuthUI.IdpConfig.TwitterBuilder ().build ()))
                        .setIsSmartLockEnabled (false, true)
                        .build (),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        // Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn (requestCode, resultCode, data);

    }

    // Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {

        IdpResponse response = IdpResponse.fromResultIntent (data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                Utils.showSnackBar (this.mConstraintLayout, getString (R.string.connection_succeed));

                if (this.getCurrentUser () != null) {
                    this.createUserInFirestore ();
                    this.startMainActivity ();
                    //   this.createUserInFirestore ();
                }
                //        else { this.createUserInFirestore (); }

            } else { // ERRORS
                if (response == null) {
                    Utils.showSnackBar (this.mConstraintLayout, getString (R.string.error_authentication_canceled));
                } else if (Objects.requireNonNull (response.getError ()).getErrorCode () == ErrorCodes.NO_NETWORK) {
                    Utils.showSnackBar (this.mConstraintLayout, getString (R.string.error_no_internet));
                } else if (response.getError ().getErrorCode () == ErrorCodes.UNKNOWN_ERROR) {
                    Utils.showSnackBar (this.mConstraintLayout, getString (R.string.error_unknown_error));
                }
            }
        }
    }

    /**
     * Http request that create user in firestore
     */
    private void createUserInFirestore() {


        if (this.getCurrentUser () != null) {


            String urlPicture = ( this.getCurrentUser ().getPhotoUrl () != null ) ? this.getCurrentUser ().getPhotoUrl ().toString () : null;
            String username = this.getCurrentUser ().getDisplayName ();
            String resto = "";
            String placeId = "";
            String uid = this.getCurrentUser ().getUid ();
            abbesolo.com.go4Lunch.firebase.UsersHelper.createWorker (username, urlPicture, resto, placeId, uid).addOnFailureListener (this.onFailureListener ());
        }

    }


    /**
     * Start MainActivity after login
     */
    private void startMainActivity() {
        Intent intent = new Intent (this, abbesolo.com.go4Lunch.ui.activity.MainActivity.class);
        startActivity (intent);
    }


}
