package com.example.saby;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllCategoryPage extends AppCompatActivity {
    ImageButton btnBack;
    RecyclerView rvCategory;
    private DatabaseReference databaseReference;
    ArrayList<CategoryModel> categoryList = new ArrayList<>();
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorypage);

        btnBack = findViewById(R.id.btn_back);
        rvCategory = findViewById(R.id.rv_categories);

        rvCategory.setLayoutManager(new GridLayoutManager(this, 2));
        categoryAdapter = new CategoryAdapter(categoryList, this);
        rvCategory.setAdapter(categoryAdapter);

        initCategory();

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, Homepage.class);
            startActivity(intent);
        });
    }

    private void initCategory() {
        databaseReference = FirebaseDatabase.getInstance("https://safarbuddy-fc40b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Category");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    categoryList.add(dataSnapshot.getValue(CategoryModel.class));
                }
                categoryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AllCategoryPage.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
