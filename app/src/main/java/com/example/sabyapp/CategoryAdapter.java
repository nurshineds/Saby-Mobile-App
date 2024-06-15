package com.example.saby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

    private List<CategoryModel> categoryList;
    private Context context;
    private int maxItem;

    public CategoryAdapter(List<CategoryModel> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }
    public CategoryAdapter(List<CategoryModel> categoryList, Context context, int maxItem) {
        this.categoryList = categoryList;
        this.context = context;
        this.maxItem = maxItem;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recom_destinate, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder holder, int position) {
        holder.CategoryName.setText(categoryList.get(position).getCategoryName());

        Glide.with(context)
                .load(categoryList.get(position).getCategoryThumb())
                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(holder.CategoryThumb);
    }

    @Override
    public int getItemCount() {
        return Math.min(categoryList.size(), maxItem);
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView CategoryThumb;
        TextView CategoryName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            CategoryThumb = itemView.findViewById(R.id.iv_category);
            CategoryName = itemView.findViewById(R.id.tv_category);
        }
    }
}