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

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.viewHolder> {

    private List<CountryModel> countryList;
    private Context context;
    private int maxItem;

    public CountryAdapter(List<CountryModel> countryList, Context context) {
        this.countryList = countryList;
        this.context = context;
    }

    public CountryAdapter(List<CountryModel> countryList, Context context, int maxItem) {
        this.countryList = countryList;
        this.context = context;
        this.maxItem = maxItem;
    }

    @NonNull
    @Override
    public CountryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recom_destinate, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.viewHolder holder, int position) {
        holder.CountryName.setText(countryList.get(position).getCountryName());

        Glide.with(context)
                .load(countryList.get(position).getCountryThumb())
                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(holder.CountryThumb);
    }

    @Override
    public int getItemCount() {
        return Math.min(countryList.size(), maxItem);
    }

    public void filter(String text) {
        countryList.clear();
        if (text.isEmpty()) {
            countryList.addAll(countryList);
        } else {
            text = text.toLowerCase();
            for (CountryModel item : countryList) {
                if (item.getCountryName().toLowerCase().contains(text)) {
                    countryList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setCountryList(ArrayList<CountryModel> countryList) {
        this.countryList = countryList;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView CountryThumb;
        TextView CountryName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            CountryThumb = itemView.findViewById(R.id.iv_category);
            CountryName = itemView.findViewById(R.id.tv_category);
        }
    }
}