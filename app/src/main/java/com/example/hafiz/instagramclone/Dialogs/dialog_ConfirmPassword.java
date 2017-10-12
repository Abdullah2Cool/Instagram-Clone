package com.example.hafiz.instagramclone.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hafiz.instagramclone.R;

/**
 * Created by hafiz on 10/10/2017.
 */

public class dialog_ConfirmPassword extends DialogFragment {

    private static final String TAG = "dialog_ConfirmPassword";

    public interface OnConfirmPasswordListener {
        public void onConfirmPassword(String password);
    }

    OnConfirmPasswordListener onConfirmPasswordListener;

    TextView mPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_password, container, false);
        Log.d(TAG, "onCreateView: started.");
        mPassword = (TextView) view.findViewById(R.id.confirm_password);

        TextView cancelDialog = view.findViewById(R.id.dialogCancel);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: closing the dialog.");
                getDialog().dismiss();
            }
        });

        TextView confirmDialog = view.findViewById(R.id.dialogConfirm);
        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: confirming captured password.");
                String password = mPassword.getText().toString();
                if (!password.equals("")) {
                    onConfirmPasswordListener.onConfirmPassword(password);
                    getDialog().dismiss();
                } else {
                    Toast.makeText(getActivity(), "You must enter a password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onConfirmPasswordListener = (OnConfirmPasswordListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.d(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}
