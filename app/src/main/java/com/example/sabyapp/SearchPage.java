package com.example.saby;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {
    SearchView svSearch;
    ImageButton btnBack;
    RecyclerView rvSearchResults;
    private DatabaseReference databaseReference;
    private CountryAdapter countryAdapter;
    private ArrayList<CountryModel> oriCountryList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpage);

        svSearch = findViewById(R.id.sv_search);
        btnBack = findViewById(R.id.btn_back);
        rvSearchResults = findViewById(R.id.rv_result);

        rvSearchResults.setLayoutManager(new GridLayoutManager(this, 2));
        countryAdapter = new CountryAdapter(new ArrayList<>(), this, 10);
        rvSearchResults.setAdapter(countryAdapter);

        databaseReference = FirebaseDatabase.getInstance("https://safarbuddy-fc40b-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Country");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CountryModel> countryList = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    countryList.add(dataSnapshot.getValue(CountryModel.class));
                }
                oriCountryList = new ArrayList<>(countryList);
                countryAdapter.setCountryList(countryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchPage.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, Homepage.class);
            startActivity(intent);
        });

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryAdapter.filter(newText);
                return true;
            }
        });
    }
}

