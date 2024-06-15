package com.example.projek;

import android.net.Uri;

public class Ulasan {
    private String username;
    private String review;
    private Uri imageUri;

    public Ulasan(String username, String review, Uri imageUri) {
        this.username = username;
        this.review = review;
        this.imageUri = imageUri;
    }

    public String getUsername() {
        return username;
    }

    public String getReview() {
        return review;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
