package com.example.appstore.View;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.Adapter.CategoryItemsListAdapter;
import com.example.appstore.Adapter.NavProListAdapter;
import com.example.appstore.Model.CategoriesItem;
import com.example.appstore.Model.ResponseModel;
import com.example.appstore.Network.AppRepository;
import com.example.appstore.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavProListFragment extends Fragment implements AppRepository.AppRepoCallBack {

    public static final String ARGS_NAV_WHICH_PRO = "ARGS_NAV_WHICH_PRO";
    public static final String BEST_PRODUCT = "BEST_PRODUCT";
    public static final String RECENT_PRODUCT = "RECENT_PRODUCT";
    public static final String MOST_VISITED = "MOST_VISITED";

    private AppRepository mAppRepository;
    private List<ResponseModel> mResponseModels = new ArrayList<>();
    private NavProListAdapter mAdapter;
    private String mWhichPro;
    private RecyclerView mRecyclerView;

    public static NavProListFragment newInstance(String whichPro) {

        Bundle args = new Bundle();

        args.putString(ARGS_NAV_WHICH_PRO, whichPro);
        NavProListFragment fragment = new NavProListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NavProListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWhichPro = getArguments().getString(ARGS_NAV_WHICH_PRO);
        mAppRepository = AppRepository.getInstance(this);

        try {
            if (mWhichPro.equals(BEST_PRODUCT))
                mAppRepository.getBestProducts();
            else if (mWhichPro.equals(RECENT_PRODUCT))
                mAppRepository.getRecentProducts();
            else if (mWhichPro.equals(MOST_VISITED))
                mAppRepository.getMostVisitedProducts();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_pro_list, container, false);

        setRecycle(view);
        setAdapter(mResponseModels);

        return view;
    }

    private void setAdapter(List<ResponseModel> list) {
        mAdapter = new NavProListAdapter(getContext());
        mAdapter.setResponseModels(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setRecycle(View view) {
        mRecyclerView = view.findViewById(R.id.recycle_nav_pro);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResponseRecent(List<ResponseModel> list) {
        mResponseModels = list;
        setAdapter( list);
    }

    @Override
    public void onResponseBest(List<ResponseModel> list) {
        mResponseModels = list;
        setAdapter( list);
    }

    @Override
    public void onResponsePopular(List<ResponseModel> list) {
        mResponseModels = list;
        setAdapter( list);
    }

    @Override
    public void onListCategoryResponse(List<CategoriesItem> listCategory) {

    }

    @Override
    public void onProListSubCategoryResponse(List<ResponseModel> proSubCatList) {

    }

    @Override
    public void onResponseProduct(ResponseModel prodect) {

    }
}
