package com.example.hafiz.instagramclone.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hafiz on 9/21/2017.
 */

// same as util_SectionsPagerAdapter but this one loads fragments as needed
public class util_SectionsStatePagerAdapter extends FragmentStatePagerAdapter {

    private final List <Fragment> mFragmentList = new ArrayList<>();
    private final HashMap <Fragment, Integer> mFragments = new HashMap<>();
    private final HashMap <String, Integer> mFragmentNumbers = new HashMap<>();
    private final HashMap <Integer, String> mFragmentNames = new HashMap<>();

    public util_SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String fragmentName) {
        mFragmentList.add(fragment);
        mFragments.put(fragment, mFragmentList.size() - 1);
        mFragmentNumbers.put(fragmentName, mFragmentList.size() - 1);
        mFragmentNames.put(mFragmentList.size() -1, fragmentName);
    }

    public Integer getFragmentNumber (String fragmentName) {
        if (mFragmentNumbers.containsKey(fragmentName)) {
            return mFragmentNumbers.get(fragmentName);
        }
        return null;
    }

    public Integer getFragmentNumber (Fragment fragment) {
        if (mFragmentNumbers.containsKey(fragment)) {
            return mFragmentNumbers.get(fragment);
        }
        return null;
    }

    public String getFragmentName (Fragment fragment) {
        if (mFragmentNames.containsKey(fragment)) {
            return mFragmentNames.get(fragment);
        }
        return null;
    }
}
