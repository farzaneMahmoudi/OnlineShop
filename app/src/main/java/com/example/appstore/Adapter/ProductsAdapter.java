package com.example.appstore.Adapter;

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

import com.example.appstore.Model.ImagesItem;
import com.example.appstore.Model.ResponseModel;
import com.example.appstore.R;
import com.example.appstore.View.DetailProductActivity;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.RecentProductViewHolder> {


    private List<ResponseModel> mResponseModelList;
    private Context mContext;


    public ProductsAdapter(Context context){
        mContext = context;
    }

    public void setList(List responseList) {
        mResponseModelList = responseList;
    }

    @NonNull
    @Override
    public RecentProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new RecentProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentProductViewHolder holder, int position) {
        holder.bind(mResponseModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mResponseModelList.size();
    }

    public class RecentProductViewHolder extends RecyclerView.ViewHolder {
        ResponseModel mResponseModel;
        ImageView productImage;
        TextView productName;
        TextView originalPrice;
        TextView salePrice;

        public RecentProductViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.pro_img);
            productName = itemView.findViewById(R.id.pro_name);
            originalPrice = itemView.findViewById(R.id.original_price);
            salePrice = itemView.findViewById(R.id.sale_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = DetailProductActivity.newIntent(mContext,mResponseModel.getId());
                    mContext.startActivity(intent);
                }
            });

        }

        public void bind(ResponseModel responseModel){
            mResponseModel = responseModel;
            productName.setText(responseModel.getName());
            String original = responseModel.getRegularPrice();
            String sale = responseModel.getSalePrice();
            originalPrice.setText(original);
            if (!sale.equalsIgnoreCase("")){
                salePrice.setText(sale);
                originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                originalPrice.setPaintFlags( originalPrice.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                salePrice.setVisibility(View.INVISIBLE);
                originalPrice.setGravity(Gravity.CENTER_VERTICAL);
            }
            ImagesItem src = responseModel.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc())).into(productImage);

        }
    }
}
