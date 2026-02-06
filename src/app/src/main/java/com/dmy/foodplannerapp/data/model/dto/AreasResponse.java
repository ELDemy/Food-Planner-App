package com.dmy.foodplannerapp.data.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreasResponse {
    @SerializedName("meals")
    List<AreaDto> areas;
}
