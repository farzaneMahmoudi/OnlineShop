package com.example.appstore.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.appstore.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText mUsername, mPassword;
    private TextInputLayout mFormUsername, mFormPassword;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public LoginFragment() {
        // Required empty public constructor
    }
    private void initUI(View view) {
        mPassword = view.findViewById(R.id.password_text);
        mUsername = view.findViewById(R.id.email_phone_number_text);
        mFormUsername = view.findViewById(R.id.email_phone_number);
        mFormPassword = view.findViewById(R.id.password);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);

        return view;
    }

}
