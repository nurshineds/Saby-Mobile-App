package com.example.projek;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivityListUlasan extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UlasanAdapter ulasanAdapter;
    private List<Ulasan> ulasanList;
    private View unggahButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulasan_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ulasanList = new ArrayList<>();

        ulasanAdapter = new UlasanAdapter(ulasanList);
        recyclerView.setAdapter(ulasanAdapter);

        handleIncomingReview(getIntent());
    }

    private void handleIncomingReview(Intent intent) {
        if (intent != null) {
            String review = intent.getStringExtra("review");
            String imageUriString = intent.getStringExtra("imageUri");

            if (review != null) {
                Uri imageUri = imageUriString != null ? Uri.parse(imageUriString) : null;
                ulasanList.add(new Ulasan("Rania Jilan", review, imageUri));
                ulasanAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            handleIncomingReview(data);
        }
    }
}
