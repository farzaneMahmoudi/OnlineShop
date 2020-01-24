package com.example.appstore.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.appstore.R;
import com.example.appstore.databinding.FragmentSearchBinding;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.view.Adapter.NavProListAdapter;
import com.example.appstore.view.Adapter.ProductsAdapter;
import com.example.appstore.viewModel.SearchFragmentViewModel;

import java.util.List;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends ConnectivityCheckFragment {

    private FragmentSearchBinding mBinding;
    private SearchFragmentViewModel mViewModel;
    private NavProListAdapter mProductsAdapter;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchFragmentViewModel.class);


    }

    private void setAdapter(List<ResponseModel> list) {
        if(mProductsAdapter == null){
            mProductsAdapter = new NavProListAdapter(getContext(),list);
            mBinding.searchResultProductRecycle.setAdapter(mProductsAdapter);
        }
        else {
            mProductsAdapter.setResponseModels(list);
            mProductsAdapter.notifyDataSetChanged();
        }
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
        mBinding.searchResultProductRecycle.setLayoutManager
                (new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
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
                mViewModel.getSearchQuery(query);
                mViewModel.getData().observe(SearchFragment.this, (List<ResponseModel> responseModels) -> {
                    setAdapter(responseModels);
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

}
