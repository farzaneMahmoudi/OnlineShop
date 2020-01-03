package com.example.appstore.view.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.appstore.model.ResponseModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mImages=new ArrayList<>();

    public SliderAdapter(Context context,List<ResponseModel> images){
        mContext = context;
        List<String> mImages=new ArrayList<>();
        for (ResponseModel responseModel:images){

            mImages.add(responseModel.getImages().get(0).getSrc());
        }
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(mContext).load(mImages.get(position)).fit().centerCrop().into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
