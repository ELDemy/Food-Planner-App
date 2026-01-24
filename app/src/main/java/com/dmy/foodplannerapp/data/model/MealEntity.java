package com.dmy.foodplannerapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "meals")
public class MealEntity implements Parcelable {

    public static final Creator<MealEntity> CREATOR = new Creator<>() {
        @Override
        public MealEntity createFromParcel(Parcel in) {
            return new MealEntity(in);
        }

        @Override
        public MealEntity[] newArray(int size) {
            return new MealEntity[size];
        }
    };

    @PrimaryKey
    @NonNull
    @SerializedName("idMeal")
    private final String id;
    @SerializedName("strMeal")
    private final String name;
    @SerializedName("strInstructions")
    private final String instructions;
    @SerializedName("strMealThumb")
    private final String thumbnail;
    @Ignore
    private List<Ingredient> ingredients;
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;

    protected MealEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        instructions = in.readString();
        thumbnail = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<Ingredient> getIngredients() {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
            addIngredient(strIngredient1, strMeasure1);
            addIngredient(strIngredient2, strMeasure2);
            addIngredient(strIngredient3, strMeasure3);
            addIngredient(strIngredient4, strMeasure4);
            addIngredient(strIngredient5, strMeasure5);
        }
        return ingredients;
    }

    private void addIngredient(String name, String quantity) {
        if (name != null && !name.trim().isEmpty()) {
            ingredients.add(new Ingredient(name, quantity));
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(instructions);
        dest.writeString(thumbnail);
        dest.writeTypedList(getIngredients());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "MealEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", ingredients=" + getIngredients() +
                '}';
    }
}

