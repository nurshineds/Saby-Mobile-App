package com.example.saby;

public class CountryModel {
    private int CountryID;
    private String CountryName;
    private String CountryThumb;

    public CountryModel(){
    }

    public CountryModel(int CountryID, String CountryName, String CountryThumb){
        this.CountryID = CountryID;
        this.CountryName = CountryName;
        this.CountryThumb = CountryThumb;
    }

    public int getCountryID() {
        return CountryID;
    }

    public void setCountryID(int countryID) {
        CountryID = countryID;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getCountryThumb() {
        return CountryThumb;
    }

    public void setCountryThumb(String countryThumb) {
        CountryThumb = countryThumb;
    }
}
