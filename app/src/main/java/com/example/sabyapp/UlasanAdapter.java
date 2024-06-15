package com.example.projek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UlasanAdapter extends RecyclerView.Adapter<UlasanAdapter.UlasanViewHolder> {

    private List<Ulasan> ulasanList;
    private Context context;

    public UlasanAdapter(List<Ulasan> ulasanList) {
        this.ulasanList = ulasanList;
    }

    @NonNull
    @Override
    public UlasanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_ulasan, parent, false);
        return new UlasanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UlasanViewHolder holder, int position) {
        Ulasan ulasan = ulasanList.get(position);
        holder.username.setText(ulasan.getUsername());
        holder.review.setText(ulasan.getReview());
        if (ulasan.getImageUri() != null) {
            holder.imageView.setImageURI(ulasan.getImageUri());
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return ulasanList.size();
    }

    static class UlasanViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView review;
        ImageView imageView;

        UlasanViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.namaUser);
            review = itemView.findViewById(R.id.ulasan);
            imageView = itemView.findViewById(R.id.fotoUlasan);
        }
    }
}
