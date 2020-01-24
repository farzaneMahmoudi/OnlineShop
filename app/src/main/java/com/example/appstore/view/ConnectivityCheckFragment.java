package com.example.appstore.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appstore.R;
import com.example.appstore.utils.NetworkHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectivityCheckFragment extends Fragment {


    public ConnectivityCheckFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NetworkHelper.isConnected(getActivity())) {
            getActivity().startActivity(NoConnectionActivity.newIntent(getActivity()));
            getActivity().finish();
        }
    }

}
