package com.example.appstore.View;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.Adapter.CategoryItemsListAdapter;
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
public class ProductListFragment extends Fragment implements AppRepository.AppRepoCallBack {

    public static final String ARGS_CATEGORY_FRAGMENT_CATEGORY_ID = "args_category_fragment_category_id";

    private int mCategoryId;
    private AppRepository mAppRepository;

    private RecyclerView mRecyclerView;
    private CategoryItemsListAdapter mCategoryItemsListAdapter;
    private List<ResponseModel> mResponseModels = new ArrayList<>();

    public static ProductListFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ARGS_CATEGORY_FRAGMENT_CATEGORY_ID, id);

        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategoryId = getArguments().getInt(ARGS_CATEGORY_FRAGMENT_CATEGORY_ID);
        mAppRepository = AppRepository.getInstance(this);

        try {
            mAppRepository.getProSubCatList(mCategoryId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        setRecycle(view);

        setAdapter(mResponseModels);

        return view;
    }

    private void setAdapter(List<ResponseModel> list) {
        mCategoryItemsListAdapter = new CategoryItemsListAdapter(getContext());
        mCategoryItemsListAdapter.setList( list);
        mRecyclerView.setAdapter(mCategoryItemsListAdapter);
    }

    private void setRecycle(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_category_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onResponseRecent(List<ResponseModel> list) {

    }

    @Override
    public void onResponseBest(List<ResponseModel> list) {

    }

    @Override
    public void onResponsePopular(List<ResponseModel> list) {

    }

    @Override
    public void onListCategoryResponse(List<CategoriesItem> listCategory) {

    }

    @Override
    public void onProListSubCategoryResponse(List<ResponseModel> list) {
       mResponseModels = list;
        setAdapter( list);
    }

    @Override
    public void onResponseProduct(ResponseModel prodect) {

    }
}
