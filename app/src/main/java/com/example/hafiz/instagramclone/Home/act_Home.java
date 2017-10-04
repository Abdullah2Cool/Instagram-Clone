package com.example.hafiz.instagramclone.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hafiz.instagramclone.Login.act_Login;
import com.example.hafiz.instagramclone.R;
import com.example.hafiz.instagramclone.Utils.util_BottomNavigationViewHelper;
import com.example.hafiz.instagramclone.Utils.util_SectionsPagerAdapter;
import com.example.hafiz.instagramclone.Utils.util_UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

public class act_Home extends AppCompatActivity {

    private static final String TAG = "act_Home";
    private static final int ACITIVITY_NUM = 0; // relates to it's index in the bottom navigation
    private Context mContext = act_Home.this; // create for later ease of use

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupFirebaseAuth();

        Log.d(TAG, "onCreate: starting");
        initImageLoader();
        setupBottomNavigationView();
        setupViewPager();

//        mAuth.signOut();
    }

    private void initImageLoader() {
        // setup the image loader with the third party universal image loader and it's config
        util_UniversalImageLoader universalImageLoader = new util_UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        // setting up the bottom navigation layout/menu with third party library
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        util_BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        util_BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);

        // highlight the correct tab in the bottom navigation
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACITIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setupViewPager() {
        // add individual fragments to the tabs
        util_SectionsPagerAdapter adapter = new util_SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new frag_Camera());
        adapter.addFragment(new frag_Home());
        adapter.addFragment(new frag_Messages());

        // setup the viewpager with the adapter
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        // setup the tabs with the viewpager
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // set the icons for each of the tabs
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_instagram);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);
    }

     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * checks to see if the @param 'user' is logged in
     *
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user) {
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if (user == null) {
            // if the user isn't logged in, start the login activity
            Intent intent = new Intent(mContext, act_Login.class);
            startActivity(intent);
        }
    }

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

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
    }

    @Override
    public void onStart() {
        super.onStart();
        // set the listener for the current user
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}