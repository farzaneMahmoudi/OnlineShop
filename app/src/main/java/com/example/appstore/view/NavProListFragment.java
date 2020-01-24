package com.example.appstore.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.view.Adapter.EndlessRecyclerViewScrollListener;
import com.example.appstore.view.Adapter.NavProListAdapter;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.network.AppRepository;
import com.example.appstore.R;
import com.example.appstore.viewModel.NavProListFragmentViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavProListFragment extends ConnectivityCheckFragment {

    public static final String ARGS_NAV_WHICH_PRO = "ARGS_NAV_WHICH_PRO";
    public static final String BEST_PRODUCT = "BEST_PRODUCT";
    public static final String RECENT_PRODUCT = "RECENT_PRODUCT";
    public static final String MOST_VISITED = "MOST_VISITED";

    private AppRepository mAppRepository;
    private List<ResponseModel> mResponseModels = new ArrayList<>();
    private NavProListAdapter mAdapter;
    private String mWhichPro;
    private int pageNumber = 1;
    private NavProListFragmentViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private EndlessRecyclerViewScrollListener mEndlessRecycler;

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
        mAppRepository = AppRepository.getInstance();
        mViewModel = ViewModelProviders.of(this).get(NavProListFragmentViewModel.class);

        try {
            mViewModel.loadProList(mWhichPro);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mWhichPro.equals(BEST_PRODUCT))
            mViewModel.getBestProList().observe(this, models -> {
                mResponseModels.addAll(models);
                setAdapter(models);
            });
        if (mWhichPro.equals(RECENT_PRODUCT))
            mViewModel.getRecentProList().observe(this, models -> {
                mResponseModels.addAll(models);
                setAdapter(models);
            });
        if (mWhichPro.equals(MOST_VISITED))
            mViewModel.getMostVisitedProList().observe(this, models -> {
                mResponseModels.addAll(models);
                setAdapter(models);
            });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_pro_list, container, false);

        setRecycle(view);

        mEndlessRecycler = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNumber++;
                try {
                    if (mWhichPro.equals(BEST_PRODUCT))
                        mAppRepository.getBestProPerPage(pageNumber);
                    else if (mWhichPro.equals(RECENT_PRODUCT))
                        mAppRepository.getAllProPerPage(pageNumber);
                    else if (mWhichPro.equals(MOST_VISITED))
                        mAppRepository.getMostVisitedProPerPage(pageNumber);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mRecyclerView.addOnScrollListener(mEndlessRecycler);
        return view;
    }

    private void setAdapter(List<ResponseModel> list) {
        if (mAdapter == null) {
            mAdapter = new NavProListAdapter(getContext(),list);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setResponseModels(mResponseModels);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void setRecycle(View view) {
        mRecyclerView = view.findViewById(R.id.recycle_nav_pro);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

}
