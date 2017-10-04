package com.example.hafiz.instagramclone.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hafiz.instagramclone.R;
import com.example.hafiz.instagramclone.Utils.util_UniversalImageLoader;

/**
 * Created by hafiz on 9/21/2017.
 */

public class frag_EditProfile extends Fragment {

    private static final String TAG = "frag_EditProfile";
    private ImageView mProfilePhote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfilePhote = view.findViewById(R.id.profile_photo);
        setProfileImage();
        // back arrow for Profile Activity
        ImageView backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Going back to Profile Activity");
                getActivity().finish();
            }
        });

        return view;
    }

    private void setProfileImage () {
        Log.d(TAG, "setProfileImage: setting profile image");
        String imgURL = "android.suvenconsultants.com/newimage/android-developer2.png";
        util_UniversalImageLoader.setImage(imgURL, mProfilePhote, null, "http://");
    }
}
