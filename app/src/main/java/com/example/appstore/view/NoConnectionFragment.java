package com.example.appstore.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoConnectionFragment extends Fragment {

    public static NoConnectionFragment newInstance() {

        Bundle args = new Bundle();

        NoConnectionFragment fragment = new NoConnectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NoConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_connection, container, false);
        return view;
    }

}
