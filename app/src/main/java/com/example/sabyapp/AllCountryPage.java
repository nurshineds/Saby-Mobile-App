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

public class AllCountryPage extends AppCompatActivity {
    ImageButton btnBack;
    RecyclerView rvCountry;
    private DatabaseReference databaseReference;
    ArrayList<CountryModel> countryList = new ArrayList<>();
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recompage);

        btnBack = findViewById(R.id.btn_back);
        rvCountry = findViewById(R.id.rv_countries);

        rvCountry.setLayoutManager(new GridLayoutManager(this, 2));
        countryAdapter = new CountryAdapter(countryList, this);
        rvCountry.setAdapter(countryAdapter);

        initCountry();

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, Homepage.class);
            startActivity(intent);
        });
    }

    private void initCountry() {
        databaseReference = FirebaseDatabase.getInstance("https://safarbuddy-fc40b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Country");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countryList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    countryList.add(dataSnapshot.getValue(CountryModel.class));
                }
                countryAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AllCountryPage.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
