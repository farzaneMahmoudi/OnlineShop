package com.example.appstore.view;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appstore.model.Category;
import com.example.appstore.view.Adapter.CategoryItemsListAdapter;
import com.example.appstore.R;
import com.example.appstore.viewModel.CategoryProListFragmentViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryListFragment extends ConnectivityCheckFragment {

    public static final String ARGS_CATEGORY_FRAGMENT_CATEGORY_ID = "args_category_fragment_category_id";

    private RecyclerView mRecyclerView;

    private int mCategoryId;
    private CategoryItemsListAdapter mCategoryItemsListAdapter;
    private List<Category> mCategoriesItems = new ArrayList<>();
    private CategoryProListFragmentViewModel mViewModel;

    public static SubCategoryListFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ARGS_CATEGORY_FRAGMENT_CATEGORY_ID, id);

        SubCategoryListFragment fragment = new SubCategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SubCategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategoryId = getArguments().getInt(ARGS_CATEGORY_FRAGMENT_CATEGORY_ID);
        mViewModel = ViewModelProviders.of(this).get(CategoryProListFragmentViewModel.class);

        try {
            mViewModel.loadCatProList(81).observe(this, responseModels -> {
                mCategoriesItems = responseModels;
                setAdapter(mCategoriesItems);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_product_list, container, false);

        setRecycle(view);
        setAdapter(mCategoriesItems);

        return view;
    }

    private void setAdapter(List<Category> list) {
        if (mCategoryItemsListAdapter==null) {
            mCategoryItemsListAdapter = new CategoryItemsListAdapter(getContext(),list);
            mRecyclerView.setAdapter(mCategoryItemsListAdapter);
        } else {
            mCategoryItemsListAdapter.setList(list);
            mCategoryItemsListAdapter.notifyDataSetChanged();
        }
    }

    private void setRecycle(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_category_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
