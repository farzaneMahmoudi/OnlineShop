package com.example.appstore.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.R;
import com.example.appstore.databinding.FragmentSearchBinding;
/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends ConnectivityCheckFragment {

    private FragmentSearchBinding mBinding;

    public static SearchFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_search, container, false);
        initUi();

        return mBinding.getRoot();
    }

    private void initUi() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.toolbarSearch);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem searchMenuItem = menu.findItem(R.id.search_menu_item_icon);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = SearchResultActivity.newIntent(getContext(),query);

                getActivity().startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

}
