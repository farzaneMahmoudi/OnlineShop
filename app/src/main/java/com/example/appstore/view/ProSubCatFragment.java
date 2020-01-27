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

import com.example.appstore.R;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.view.Adapter.EndlessRecyclerViewScrollListener;
import com.example.appstore.view.Adapter.NavProListAdapter;
import com.example.appstore.viewModel.ProductsOfCategoryFragmentViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProSubCatFragment extends Fragment {

    public static final String ARGS_CATEGORY_ID = "args_categoryId";
    private int mCategoryId;
    private ProductsOfCategoryFragmentViewModel mViewModel;

    private List<ResponseModel> mResponseModels = new ArrayList<>();
    private NavProListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private int pageNumber=1;
    private EndlessRecyclerViewScrollListener mEndlessRecycler;
    private LinearLayoutManager mLinearLayoutManager;

    public static ProSubCatFragment newInstance(int categoryId) {

        Bundle args = new Bundle();

        args.putInt(ARGS_CATEGORY_ID,categoryId);
        ProSubCatFragment fragment = new ProSubCatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProSubCatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getInt(ARGS_CATEGORY_ID);
       mViewModel = ViewModelProviders.of(this).get(ProductsOfCategoryFragmentViewModel.class);
        try {
            mViewModel.getCatProList(mCategoryId).observe(this, new Observer<List<ResponseModel>>() {
                @Override
                public void onChanged(List<ResponseModel> list) {
                    mResponseModels.addAll(list);
                    setAdapter(list);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pro_sub_cat, container, false);

        setRecycle(view);

/*        mEndlessRecycler = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNumber++;
                try {
                       mViewModel.getCatProListPerPage(mCategoryId,pageNumber);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mRecyclerView.addOnScrollListener(mEndlessRecycler);*/

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
        mRecyclerView = view.findViewById(R.id.recycle_pro_sub_cat);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

}
