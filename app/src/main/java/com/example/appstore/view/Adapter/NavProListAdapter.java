package com.example.appstore.view.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstore.model.ImagesItem;
import com.example.appstore.model.ResponseModel;
import com.example.appstore.R;
import com.example.appstore.view.DetailProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NavProListAdapter extends RecyclerView.Adapter<NavProListAdapter.NavProListViewHolder> {

    private Context mContext;
    private List<ResponseModel> mResponseModels;

    public void setResponseModels(List<ResponseModel> responseModels) {
        mResponseModels = responseModels;
    }

    public NavProListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public NavProListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.nav_product_item, parent, false);
        return new NavProListAdapter.NavProListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavProListViewHolder holder, int position) {
        holder.bind(mResponseModels.get(position));
    }


    @Override
    public int getItemCount() {
        return mResponseModels.size();
    }

    public class NavProListViewHolder extends RecyclerView.ViewHolder {
        private TextView mBasePrice;
        private TextView mSalePrice;
        private ImageView mImageViewPro;
        private TextView mProName;

        private ResponseModel mModel;

        public NavProListViewHolder(@NonNull View itemView) {
            super(itemView);
            findView(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = DetailProductActivity.newIntent(mContext,mModel.getId());
                    mContext.startActivity(intent);
                }
            });
        }

        private void findView(@NonNull View itemView) {

            mBasePrice = itemView.findViewById(R.id.text_base_price_nav_pro);
            mSalePrice = itemView.findViewById(R.id.text_sale_price_nav_pro);
            mImageViewPro = itemView.findViewById(R.id.image_cat_list_item);
            mProName = itemView.findViewById(R.id.text_nav_item_product_name);

        }

        private void bind(ResponseModel model) {
            mModel = model;
            mProName.setText(mModel.getName());
            String basePrice = mModel.getRegularPrice();
            String salePrice = mModel.getSalePrice();
            mBasePrice.setText(basePrice);
            if (!salePrice.equalsIgnoreCase("")) {
                mSalePrice.setText(salePrice);
                mBasePrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                mBasePrice.setPaintFlags(mBasePrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                mSalePrice.setVisibility(View.INVISIBLE);
                mBasePrice.setGravity(Gravity.CENTER_VERTICAL);
            }
            ImagesItem src = mModel.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc())).into(mImageViewPro);
        }
    }
}
