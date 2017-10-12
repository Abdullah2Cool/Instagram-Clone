package com.example.hafiz.instagramclone.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hafiz.instagramclone.Dialogs.dialog_ConfirmPassword;
import com.example.hafiz.instagramclone.Models.User;
import com.example.hafiz.instagramclone.Models.UserAccountSettings;
import com.example.hafiz.instagramclone.Models.UserSettings;
import com.example.hafiz.instagramclone.R;
import com.example.hafiz.instagramclone.Utils.util_FirebaseMethods;
import com.example.hafiz.instagramclone.Utils.util_UniversalImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hafiz on 9/21/2017.
 */

public class frag_EditProfile extends Fragment implements dialog_ConfirmPassword.OnConfirmPasswordListener {

    private static final String TAG = "frag_EditProfile";

    //EditProfile Fragment widgets
    private EditText mDisplayName, mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;


    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private util_FirebaseMethods mFirebaseMethods;
    private String userID;
    private UserSettings mUserSettings;

    @Override
    public void onConfirmPassword(String password) {
        // don't ever do this
        Log.d(TAG, "onConfirmPassword: got the password: " + password);

        // re-authenticate the user
        FirebaseUser user = mAuth.getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "onComplete: re-authentication failed.");
                        }
                        Log.d(TAG, "User re-authenticated.");

                        // check to see if the emial is not already present in the database
                        mAuth.fetchProvidersForEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                if (task.isSuccessful()) {
                                    try {
                                        if (task.getResult().getProviders().size() == 1) {
                                            Log.d(TAG, "onComplete: that email is already in use.");
                                            Toast.makeText(getActivity(), "That email is already in use.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d(TAG, "onComplete: that email is available.");

                                            // the email is available so update it
                                            mAuth.getCurrentUser().updateEmail(mEmail.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d(TAG, "User email address updated.");
                                                                Toast.makeText(getActivity(), "That email is updated.", Toast.LENGTH_SHORT).show();
                                                                mFirebaseMethods.updateEmail(mEmail.getText().toString());
                                                            }
                                                        }
                                                    });
                                        }
                                    } catch (NullPointerException e) {
                                        Log.d(TAG, "onComplete: NullPointerException: " + e.getMessage());
                                    }
                                }
                            }
                        });
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfilePhoto = view.findViewById(R.id.profile_photo);
        mDisplayName = view.findViewById(R.id.display_name);
        mUsername = view.findViewById(R.id.username);
        mWebsite = view.findViewById(R.id.website);
        mDescription = view.findViewById(R.id.description);
        mEmail = view.findViewById(R.id.email);
        mPhoneNumber = view.findViewById(R.id.phoneNumber);
        mChangeProfilePhoto = view.findViewById(R.id.changeProfilePhoto);

        mFirebaseMethods = new util_FirebaseMethods(getActivity());

        setupFirebaseAuth();

//        setProfileImage();
        // back arrow for Profile Activity
        ImageView backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Going back to Profile Activity");
                getActivity().finish();
            }
        });

        ImageView checkmark = view.findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: saving profile settings.");
                saveProfileSettings();
            }
        });

        return view;
    }

    private void setProfileWidgets(UserSettings userSettings) {
//        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieved from firebase database");

        mUserSettings = userSettings;

        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getUserAccountSettings();

        util_UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(user.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(user.getEmail());
        mPhoneNumber.setText(String.valueOf(user.getPhone_number()));
    }

    /**
     * Check if @param username already exists in the database
     *
     * @param username
     */
    private void checkIfUsernameExists(final String username) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // add the username
                    mFirebaseMethods.updateUsername(username);
                    Toast.makeText(getActivity(), "saved username", Toast.LENGTH_SHORT).show();
                }
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    if (singleSnapshot.exists()) {
                        Log.d(TAG, "onDataChange: Found a match " + singleSnapshot.getValue(User.class).getUsername());
                        Toast.makeText(getActivity(), "That username already exits.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Retrieves the data contained in the widgets and submits it to the database
     * Before doing so it checks to make sure the username chosen is unique
     */
    private void saveProfileSettings() {
        final String displayName = mDisplayName.getText().toString();
        final String username = mUsername.getText().toString();
        final String website = mWebsite.getText().toString();
        final String description = mDescription.getText().toString();
        final String email = mEmail.getText().toString();
        final long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());


        // case 1: the user made a change to their username
        if (!mUserSettings.getUser().getUsername().equals(username)) {
            checkIfUsernameExists(username);
        }
        // case 2: the user made a change to their email
        if (!mUserSettings.getUser().getEmail().equals(email)) {
            // step 1) reauthenticate
            //          - Confirm the password and email
            dialog_ConfirmPassword confirmPassword = new dialog_ConfirmPassword();
            confirmPassword.show(getFragmentManager(), getString(R.string.confirm_password_dialog));
            confirmPassword.setTargetFragment(frag_EditProfile.this, 1);
            // step 2) check if the email is already registered
            //          - fetechProvidersForEmail(String email)
            // step 3) change the email
            //          - submit the new email to the database and authentication


            if (!mUserSettings.getUserAccountSettings().getDisplay_name().equals(displayName)) {
                // update display name
                mFirebaseMethods.updateUserAccountSettings(displayName, null, null, 0);
            }

            if (!mUserSettings.getUserAccountSettings().getWebsite().equals(website)) {
                // update display name
                mFirebaseMethods.updateUserAccountSettings(null, website, null, 0);
            }

            if (!mUserSettings.getUserAccountSettings().getDescription().equals(description)) {
                // update display name
                mFirebaseMethods.updateUserAccountSettings(null, null, description, 0);
            }

            if (phoneNumber != 1) {
                // update display name
                mFirebaseMethods.updateUserAccountSettings(null, null, null, phoneNumber);
            }
        }

    }


            /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * checks to see if the @param 'user' is logged in
     *
     * @param user
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // retrieve user info from the database
                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));
                // retrieve images for the user in question
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // set the listener for the current user
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
