package com.dmy.foodplannerapp.data.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse {
    @SerializedName("meals")
    private List<CountryDTO> countries;

    public List<CountryDTO> getCountries() {
        return countries;
    }

    @Override
    public String toString() {
        return "CountriesResponse{" +
                "countries=" + countries +
                '}';
    }
}
