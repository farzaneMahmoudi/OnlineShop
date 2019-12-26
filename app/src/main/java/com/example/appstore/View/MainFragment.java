package com.example.appstore.View;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.Adapter.ProductsAdapter;
import com.example.appstore.Model.CategoriesItem;
import com.example.appstore.Model.ResponseModel;
import com.example.appstore.Network.AppRepository;
import com.example.appstore.R;

import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.TextSliderView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements AppRepository.AppRepoCallBack {

    private ProductsAdapter mBestProductAdapter;
    private ProductsAdapter mMostVisitedAdapter;
    private ProductsAdapter mProductsAdapter;

    private RecyclerView mRecyclerViewBestProduct;
    private RecyclerView mRecyclerViewMostVisited;
    private RecyclerView mRecyclerViewRecentProducts;

    private AppRepository mAppRepository;
    private  List<String> mUrlImages = new ArrayList<>();

    private List<ResponseModel> mRecentProductList = new ArrayList<>();
    private List<ResponseModel> mBestProductList = new ArrayList<>();
    private List<ResponseModel> mMostVisitedProductList = new ArrayList<>();
    private SliderLayout mSliderLayout;

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

        mAppRepository = AppRepository.getInstance(this);
        try {
            mAppRepository.getRecentProducts();
            mAppRepository.getBestProducts();
            mAppRepository.getMostVisitedProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        findView(view);
        setUI();
        setUpAdapter();
        sliderSetup();

        return view;
    }

    private void sliderSetup() {

        for (int i=0;i<mUrlImages.size();i++) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(mUrlImages.get(i))
                    .setProgressBarVisible(true);
            mSliderLayout.addSlider(textSliderView);
        }

        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(3000);
    }

    private void setUpAdapter() {
        mProductsAdapter = new ProductsAdapter(getContext());
        mProductsAdapter.setList(mRecentProductList);
        mRecyclerViewRecentProducts.setAdapter(mProductsAdapter);

        mBestProductAdapter = new ProductsAdapter(getContext());
        mBestProductAdapter.setList(mBestProductList);
        mRecyclerViewBestProduct.setAdapter(mBestProductAdapter);

        mMostVisitedAdapter = new ProductsAdapter(getContext());
        mMostVisitedAdapter.setList(mMostVisitedProductList);
        mRecyclerViewMostVisited.setAdapter(mMostVisitedAdapter);
    }

    private void setUI() {
        mRecyclerViewRecentProducts.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mRecyclerViewBestProduct.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        mRecyclerViewMostVisited.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
    }

    private void findView(View view) {
        mRecyclerViewBestProduct = view.findViewById(R.id.best_products_recycler_view);
        mRecyclerViewMostVisited = view.findViewById(R.id.most_visited_recycler_view);
        mRecyclerViewRecentProducts = view.findViewById(R.id.recent_products_recycler_view);
        mSliderLayout=view.findViewById(R.id.slider);
    }


    @Override
    public void onResponseRecent(List<ResponseModel> list) {
        mRecentProductList = list;
        setUpAdapter();

        for (ResponseModel responseModel:mRecentProductList){
            mUrlImages.add(responseModel.getImages().get(0).getSrc());
        }
        sliderSetup();

    }

    @Override
    public void onResponseBest(List<ResponseModel> list) {
        mBestProductList = list;
        setUpAdapter();
    }

    @Override
    public void onResponsePopular(List<ResponseModel> list) {
        mMostVisitedProductList = list;
        setUpAdapter();
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
