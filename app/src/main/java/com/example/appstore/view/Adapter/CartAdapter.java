package com.example.appstore.view.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Paint;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstore.databinding.CartItemBinding;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.R;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    private List<ResponseModel> mResponseModels;

    public void setResponseModels(List<ResponseModel> responseModels) {
        mResponseModels = responseModels;
    }

    public CartAdapter(Context context, List<ResponseModel> list) {
        mContext = context;
        mResponseModels = list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.cart_item,parent,false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(mResponseModels.get(position));
    }


    @Override
    public int getItemCount() {
        return mResponseModels.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView mFinalPrice;
        private TextView mSalePrice;
        private TextView mTolalPrice;
        private ImageView mImageViewPro;
        private TextView mProName;

        private ResponseModel mModel;
        private CartItemBinding mCartItemBinding;

        public CartViewHolder(CartItemBinding cartItemBinding) {
            super(cartItemBinding.getRoot());
            mCartItemBinding = cartItemBinding;
        }

        private void bind(ResponseModel model) {
    /*        mModel = model;
            mCartItemBinding.titleTextView.setText(mModel.getName());
            String total = mModel.getRegularPrice();
            String salePrice = mModel.getSalePrice();
            mCartItemBinding.totalPriceTextView.setText(mModel.getRegularPrice());


            ImagesItem src = mModel.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc())).into(mCartItemBinding.image);*/
        }
    }
}
