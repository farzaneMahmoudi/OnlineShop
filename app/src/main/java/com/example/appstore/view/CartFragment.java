package com.example.appstore.view;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.R;
import com.example.appstore.databinding.FragmentCartBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {


   private FragmentCartBinding mBinding;

    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false);
        setUI();

        return mBinding.getRoot();
    }

    private void setUI() {
        mBinding.toolbarCart.setTitle(R.string.your_cart);
    }

}
