package com.example.appstore.View;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appstore.Model.CategoriesItem;
import com.example.appstore.Model.ImagesItem;
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
public class DetailProductFragment extends Fragment implements AppRepository.AppRepoCallBack {

    private static int mProductId;

    private AppRepository mFetchItems;
    private ResponseModel mProduct;

    private SliderLayout mSliderLayout;
    private TextView proName;
    private TextView proDescription;
    private TextView realPrice;
    private Button addToCard;

    ArrayList<String> mUrlImages ;

    public static DetailProductFragment newInstance(int id) {

        Bundle args = new Bundle();

        mProductId = id;
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
        mUrlImages = new ArrayList<>();
        mFetchItems = AppRepository.getInstance(this);
        try {
            mFetchItems.getSpecificProducts(mProductId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mProduct = new ResponseModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);

        initUI(view);
        setUi( mProduct);
        return view;
    }

    private void initUI(View view) {
        mSliderLayout = view.findViewById(R.id.slider_product_image);
        proName = view.findViewById(R.id.title);
        //proDescription = view.findViewById(R.id.description);
        realPrice = view.findViewById(R.id.cost);
        addToCard = view.findViewById(R.id.button_add_to_card);
    }


    private void sliderSetup(List<ImagesItem> imagesItems) {

        for (int i = 0; i < imagesItems.size(); i++) {
            mUrlImages.add(imagesItems.get(i).getSrc());
        }

        for (int i = 0; i < mUrlImages.size(); i++) {
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
    public void onProListSubCategoryResponse(List<ResponseModel> proSubCatList) {

    }


    @Override
    public void onResponseProduct(ResponseModel product) {
        mProduct = product;
        setUi(product);

    }

    private void setUi(ResponseModel product) {
        proName.setText(product.getName());
       // proDescription.setText(product.getDescription());
        String original = product.getRegularPrice();
        realPrice.setText(original);
        List<ImagesItem> imagesItems = product.getImages();
        if(imagesItems !=null)
        sliderSetup(imagesItems);
    }
}
