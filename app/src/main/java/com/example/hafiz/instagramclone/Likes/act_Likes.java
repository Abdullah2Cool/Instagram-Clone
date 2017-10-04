package com.example.hafiz.instagramclone.Likes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hafiz.instagramclone.R;
import com.example.hafiz.instagramclone.Utils.util_BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by hafiz on 9/18/2017.
 */

public class act_Likes extends AppCompatActivity {
    private static final String TAG = "act_Likes";
    private static final int ACITIVITY_NUM = 3;
    private Context mContext = act_Likes.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started");

        setupBottomNavigationView();
    }
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        util_BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        util_BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACITIVITY_NUM);
        menuItem.setChecked(true);
    }
}
