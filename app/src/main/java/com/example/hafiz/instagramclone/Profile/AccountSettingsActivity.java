package com.example.hafiz.instagramclone.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.hafiz.instagramclone.R;
import com.example.hafiz.instagramclone.Utils.BottomNavigationViewHelper;
import com.example.hafiz.instagramclone.Utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by hafiz on 9/20/2017.
 */

public class AccountSettingsActivity extends AppCompatActivity {

    private static final String TAG = "AccountSettingsActivity";
    private Context mContext = AccountSettingsActivity.this;
    private static final int ACITIVITY_NUM = 4;

    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);
        Log.d(TAG, "onCreate: started.");
        viewPager = findViewById(R.id.container);
        relativeLayout = findViewById(R.id.relLayout1);

        setupBottomNavigationView();
        setupSettingsList();
        setupFragments();
        // Setup back button
        ImageView imageView = findViewById(R.id.backArrow);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupFragments () {
        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        sectionsStatePagerAdapter.addFragment(new EditProfileFragment(), getString(R.string.edit_profile_fragment));
        sectionsStatePagerAdapter.addFragment(new SignOutFragment(), getString(R.string.sign_out_fragment));
    }

    private void setViewPager(int fragmentNumber) {
        relativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setViewPager: navigating to fragment#: " + fragmentNumber);
        viewPager.setAdapter(sectionsStatePagerAdapter);
        viewPager.setCurrentItem(fragmentNumber);
    }

    private void setupSettingsList() {
        Log.d(TAG, "setupSettingsList: setting up settings list");
        ListView settings = findViewById(R.id.lvAccountSettings);

        ArrayList <String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile_fragment));
        options.add(getString(R.string.sign_out_fragment));

        ArrayAdapter adapter = new ArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item, options);
        settings.setAdapter(adapter);

        settings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: Navigating to fragment position#: " + i);
                // display the correct fragment
                setViewPager(i);
            }
        });
    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACITIVITY_NUM);
        menuItem.setChecked(true);
    }
}
