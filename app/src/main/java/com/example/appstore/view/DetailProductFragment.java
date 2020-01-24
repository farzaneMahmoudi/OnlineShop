package com.example.appstore.view;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appstore.model.ImagesItem;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.network.AppRepository;
import com.example.appstore.R;
import com.example.appstore.viewModel.DetailProductFragmentViewModel;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.TextSliderView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProductFragment extends ConnectivityCheckFragment {

    public static final String ARGS_PRODUCT_ID = "args_product_id";
    private int mProductId;

    private SliderLayout mSliderLayout;
    private TextView proName;
    private TextView realPrice;
    private DetailProductFragmentViewModel mViewModel;
    private Button addToCard;
    private TextView proDescription;

    public static DetailProductFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ARGS_PRODUCT_ID, id);

        DetailProductFragment fragment = new DetailProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getInt(ARGS_PRODUCT_ID);

        mViewModel = ViewModelProviders.of(this).get(DetailProductFragmentViewModel.class);
        try {
            mViewModel.loadData(mProductId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mViewModel.getResponseModel().observe(this, responseModel -> {
            setUi(responseModel);
            sliderSetup(responseModel);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);

        initUI(view);
        return view;
    }

    private void initUI(View view) {
        mSliderLayout = view.findViewById(R.id.slider_product_image);
        proName = view.findViewById(R.id.title);
        proDescription = view.findViewById(R.id.description);
        realPrice = view.findViewById(R.id.cost);
        addToCard = view.findViewById(R.id.button_add_to_card);
    }


    private void sliderSetup(ResponseModel model) {
      //  mSliderLayout.removeAllSliders();
        for (int i = 0; i < model.getImages().size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(model.getImages().get(i).getSrc())
                    .setProgressBarVisible(true);
            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setDuration(3000);
    }

    private void setUi(ResponseModel product) {
        proName.setText(product.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            proDescription .setText(Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        } else proDescription.setText(Html.fromHtml(product.getDescription()));

        String original = product.getRegularPrice();
        realPrice.setText(original);

        if (product.getImages() != null) {
            sliderSetup(product);
        }
    }

}