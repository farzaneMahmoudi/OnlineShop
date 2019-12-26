
package com.example.appstore.Adapter;
import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryItemsListAdapter extends RecyclerView.Adapter<CategoryItemsListAdapter.ItemViewHolder> {

    private List<ResponseModel> mResponseModelList;
    private Context mContext;

    public CategoryItemsListAdapter(Context context){
        mContext = context;
    }

    public void setList(List<ResponseModel> list) {
        mResponseModelList = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_list_item, parent, false);
        return new CategoryItemsListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(mResponseModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mResponseModelList.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mTextView;
        private ResponseModel mResponseModel;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            findView(itemView);
        }

        private void findView(@NonNull View itemView) {
            mImageView = itemView.findViewById(R.id.image_cat_list_item);
            mTextView = itemView.findViewById(R.id.text_cat_item_product_name);
        }

        public void bind(ResponseModel itemModel){
            mResponseModel = itemModel;
            mTextView.setText(mResponseModel.getName());
            ImagesItem src = mResponseModel.getImages().get(0);
            Picasso.with(mContext).load(Uri.parse(src.getSrc())).into(mImageView);
        }
    }
}

