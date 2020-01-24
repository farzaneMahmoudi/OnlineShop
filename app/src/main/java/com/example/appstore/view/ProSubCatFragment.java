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
public class ProSubCatFragment extends Fragment {

    public static ProSubCatFragment newInstance() {

        Bundle args = new Bundle();

        ProSubCatFragment fragment = new ProSubCatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProSubCatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pro_sub_cat, container, false);
    }

}
