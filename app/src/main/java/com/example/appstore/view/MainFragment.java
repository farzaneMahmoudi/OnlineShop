package com.example.appstore.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appstore.view.Adapter.ProductsAdapter;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.R;

import com.example.appstore.viewModel.MainFragmentViewModel;
import com.example.appstore.databinding.FragmentMainBinding;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.TextSliderView;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends ConnectivityCheckFragment {

    private ProductsAdapter mBestProductAdapter;
    private ProductsAdapter mMostVisitedAdapter;
    private ProductsAdapter mProductsAdapter;

    private TextView mCompleteListRecent;
    private TextView mCompleteListBest;
    private TextView mCompleteListMostVisited;
    private SliderLayout mSliderLayout;

    private MainFragmentViewModel mViewModel;
    private FragmentMainBinding mBinding;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);

        mViewModel.getBestPro().observe(this, bestPro -> setUpBestProAdapter(bestPro));
        mViewModel.getMostVisitedPro().observe(this, mostVisitedPro -> setUpMostVisitedProAdapter(mostVisitedPro));
        mViewModel.getRecentPro().observe(this, recentPro -> {
            setUpRecentProAdapter(recentPro);
            sliderSetup(recentPro);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_main, container, false);
        setUI();
        clickListener();

        return mBinding.getRoot();
    }

    private void clickListener() {
        mBinding.completeListMostVisited.setOnClickListener(v -> {
            Intent intent = NavProListActivity.newIntent(getActivity(), NavProListFragment.MOST_VISITED);
            startActivity(intent);
        });

        mBinding.completeListBestPro.setOnClickListener(v -> {
            Intent intent = NavProListActivity.newIntent(getActivity(), NavProListFragment.BEST_PRODUCT);
            startActivity(intent);
        });

        mBinding.completeListRecent.setOnClickListener(v -> {
            Intent intent = NavProListActivity.newIntent(getActivity(), NavProListFragment.RECENT_PRODUCT);
            startActivity(intent);
        });
    }

    private void sliderSetup(List<ResponseModel> list) {
        for (int i = 0; i < list.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(list.get(i).getImages().get(0).getSrc())
                    .setProgressBarVisible(true);
            mBinding.slider.addSlider(textSliderView);
        }

        mBinding.slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mBinding.slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mBinding.slider.setCustomAnimation(new DescriptionAnimation());
        mBinding.slider.setDuration(3000);
    }

    private void setUpRecentProAdapter(List<ResponseModel> list) {
        if (mProductsAdapter == null) {
            mProductsAdapter = new ProductsAdapter(getContext(), list);
            mBinding.recentProductsRecyclerView.setAdapter(mProductsAdapter);
        } else {
            mProductsAdapter.setList(list);
            mProductsAdapter.notifyDataSetChanged();
        }
    }

    private void setUpBestProAdapter(List<ResponseModel> list) {
        if (mBestProductAdapter == null) {
            mBestProductAdapter = new ProductsAdapter(getContext(), list);
            mBinding.bestProductsRecyclerView.setAdapter(mBestProductAdapter);
        } else {
            mBestProductAdapter.setList(list);
            mBestProductAdapter.notifyDataSetChanged();
        }
    }

    private void setUpMostVisitedProAdapter(List<ResponseModel> list) {
        if (mMostVisitedAdapter == null) {
            mMostVisitedAdapter = new ProductsAdapter(getContext(), list);
            mBinding.mostVisitedRecyclerView.setAdapter(mMostVisitedAdapter);
        } else {
            mMostVisitedAdapter.setList(list);
            mMostVisitedAdapter.notifyDataSetChanged();
        }
    }

    private void setUI() {
        mBinding.recentProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mBinding.bestProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mBinding.mostVisitedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }

}
