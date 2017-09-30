package com.example.hafiz.instagramclone.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.hafiz.instagramclone.Models.User;
import com.example.hafiz.instagramclone.Models.UserAccountSettings;
import com.example.hafiz.instagramclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hafiz on 9/23/2017.
 */

// helper class for registrering new users.
public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private String userID;

    private Context mContext;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot) {
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + " already exists");

        User user = new User();

        for (DataSnapshot ds : dataSnapshot.child(userID).getChildren()) {
            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);
            // attach the name in the database to the user
            String UsernameinDB = ds.getValue(User.class).getUsername();
            user.setUsername(UsernameinDB);

            if (StringManipulation.expandUsername(user.getUsername()).equals(username)) {
                Log.d(TAG, "checkIfUsernameExists: Found a match");
                return true;
            }
        }
        return false;
    }

    /**
     * Register a new email and password to Firebase Authentication
     *
     * @param email
     * @param password
     * @param username
     */
    public void registerNewEmail(final String email, String password, final String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                        } else if (task.isSuccessful()) {
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }
                    }
                });
    }


    public void addNewUser(String email, String username, String description, String website, String profie_photoe) {
        User user = new User(userID, 1, email, StringManipulation.condenseUsername(username));

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        UserAccountSettings settings = new UserAccountSettings(
                description,
                username,
                0,
                0,
                0,
                profie_photoe,
                username,
                website
        );

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .setValue(settings);
    }
}
