package com.example.saby;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Homepage extends AppCompatActivity {
    ImageButton btnSearch;
    TextView tvUser, tvLocation, tvAllCountry, tvAllCategory;
    Button btnGold;
    RecyclerView rvCountry, rvCategory;
    private FusedLocationProviderClient locationProviderClient;
    private DatabaseReference databaseReference;
    ArrayList<CountryModel> countryList = new ArrayList<>();
    ArrayList<CategoryModel> categoryList = new ArrayList<>();
    CountryAdapter countryAdapter;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        btnSearch = findViewById(R.id.btn_search);
        tvUser = findViewById(R.id.tv_hiuser);
        tvLocation = findViewById(R.id.tv_location);
        tvAllCountry = findViewById(R.id.tv_allA);
        tvAllCategory = findViewById(R.id.tv_allB);
        btnGold = findViewById(R.id.bt_gold);
        rvCountry = findViewById(R.id.country_recom);
        rvCategory = findViewById(R.id.destination_recom);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(Homepage.this);

        rvCountry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        countryAdapter = new CountryAdapter(countryList, this, 6);
        rvCountry.setAdapter(countryAdapter);

        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(categoryList, this, 6);
        rvCategory.setAdapter(categoryAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String username = currentUser.getDisplayName();
            if (username != null && !username.isEmpty()) {
                tvUser.setText("Hai, " + username + "!");
            } else {
                tvUser.setText("Hai, Pengguna!");
            }
        } else {
            tvUser.setText("Hai, Pengguna!");
        }

        getLocation();
        initCountry();
        initCategory();

        btnSearch.setOnClickListener(v -> {
            Intent searchIntent = new Intent(this, SearchPage.class);
            startActivity(searchIntent);
        });

        tvAllCountry.setOnClickListener(v -> {
            Intent countryIntent = new Intent(this, AllCountryPage.class);
            startActivity(countryIntent);
        });

        tvAllCategory.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(this, AllCategoryPage.class);
            startActivity(categoryIntent);
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
                Toast.makeText(Homepage.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
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
                Toast.makeText(Homepage.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
        } else {
            locationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    getAddress(location.getLatitude(), location.getLongitude());
                } else {
                    Toast.makeText(getApplicationContext(), "Please turn on your location!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e ->
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                tvLocation.setText(address.getSubAdminArea());
            } else {
                tvLocation.setText("Location Unknown");
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

