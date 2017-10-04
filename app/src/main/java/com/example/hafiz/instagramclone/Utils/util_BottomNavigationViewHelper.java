package com.example.hafiz.instagramclone.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.hafiz.instagramclone.Home.act_Home;
import com.example.hafiz.instagramclone.Likes.act_Likes;
import com.example.hafiz.instagramclone.Profile.act_Profile;
import com.example.hafiz.instagramclone.R;
import com.example.hafiz.instagramclone.Search.act_Search;
import com.example.hafiz.instagramclone.Share.act_Share;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by hafiz on 9/18/2017.
 */

public class util_BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "setupBottomNavigationView: Setting up Bottom Navigation View");
        // setup the preferences for the bottom navigation
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // hook up each icon with the correct
                switch (item.getItemId()) {
                    case R.id.ic_house:
                        Intent intent1  = new Intent(context, act_Home.class);
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2  = new Intent(context, act_Search.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_circle:
                        Intent intent3  = new Intent(context, act_Share.class);
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_alert:
                        Intent intent4  = new Intent(context, act_Likes.class);
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_android:
                        Intent intent5  = new Intent(context, act_Profile.class);
                        context.startActivity(intent5);
                        break;

                }
                return false;
            }
        });
    }
}
