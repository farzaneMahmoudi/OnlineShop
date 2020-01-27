
package com.example.appstore.view.Adapter;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstore.model.CategoriesItem;
import com.example.appstore.R;
import com.example.appstore.model.Category;
import com.example.appstore.model.ImagesItem;
import com.example.appstore.view.DetailProductActivity;
import com.example.appstore.view.ProSubCatActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryItemsListAdapter extends RecyclerView.Adapter<CategoryItemsListAdapter.ItemViewHolder> {

    private List<CategoriesItem> mItemList;
    private Context mContext;

    public CategoryItemsListAdapter(Context context,List<CategoriesItem> itemList){
        mContext = context;
        mItemList = itemList;
    }

    public void setList(List<CategoriesItem> list) {
        mItemList = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_category_list_item, parent, false);
        return new CategoryItemsListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mTextView;
        private CategoriesItem mCategoriesItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            findView(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = ProSubCatActivity.newIntent(mContext, mCategoriesItem.getId(),mCategoriesItem.getName());
                    mContext.startActivity(intent);
                }
            });
        }

        private void findView(@NonNull View itemView) {
            mImageView = itemView.findViewById(R.id.image_cat_list_item);
            mTextView = itemView.findViewById(R.id.text_cat_item_product_name);
        }

        public void bind(CategoriesItem itemModel){
            mCategoriesItem = itemModel;
            mTextView.setText(mCategoriesItem.getName());
            Picasso.with(mContext)
                    .load(mCategoriesItem.getImage().getSrc())
                    .into(mImageView);
        }
    }
}

