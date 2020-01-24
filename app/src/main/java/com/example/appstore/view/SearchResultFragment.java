package com.example.appstore.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.R;
import com.example.appstore.databinding.FragmentSearchResultBinding;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.view.Adapter.NavProListAdapter;
import com.example.appstore.viewModel.SearchFragmentViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    private static final String ARGS_SEARCH_QUERY = "query_search";
    private String mQuery;
    private SearchFragmentViewModel mViewModel;

    private FragmentSearchResultBinding mBinding;
    private NavProListAdapter mProductsAdapter;


    public static SearchResultFragment newInstance(String query) {

        Bundle args = new Bundle();
        args.putString(ARGS_SEARCH_QUERY,query);

        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuery = getArguments().getString(ARGS_SEARCH_QUERY);
        mViewModel = ViewModelProviders.of(this).get(SearchFragmentViewModel.class);
        mViewModel.getSearchQuery(mQuery);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_search_result, container, false);
        initUi();


        mViewModel.getData().observe(SearchResultFragment.this, (List<ResponseModel> responseModels) -> {
            setAdapter(responseModels);
        });
        return mBinding.getRoot();

    }

    private void initUi() {
        mBinding.searchResultProductRecycle.setLayoutManager
                (new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.toolbarSearchResult);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        mBinding.toolbarSearchResult.setTitle(mQuery);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu,menu);

        MenuItem cart = menu.findItem(R.id.cart);
        cart.setOnMenuItemClickListener(item -> {
            Intent intent = CartActivity.newIntent(getContext());
            startActivity(intent);
            return true;
        });

        MenuItem searchMenuItem = menu.findItem(R.id.search_home);
        searchMenuItem.setOnMenuItemClickListener(item -> {
          getActivity().startActivity(SearchActivity.newIntent(getContext()));
          return true;
        });
    }

}
