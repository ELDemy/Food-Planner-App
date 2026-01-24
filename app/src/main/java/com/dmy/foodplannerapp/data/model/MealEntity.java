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

    @SerializedName("strCategory")
    private final String category;

    @SerializedName("strArea")
    private final String area;

    @SerializedName("strInstructions")
    private final String instructions;

    @SerializedName("strMealThumb")
    private final String thumbnail;

    @SerializedName("strTags")
    private final String tags;
    @SerializedName("strYoutube")
    private final String youtube;
    @SerializedName("strSource")
    private final String source;
    @SerializedName("dateModified")
    private final String dateModified;
    
    @Ignore
    private List<Ingredient> ingredients;
    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;
    private String strIngredient16;
    private String strIngredient17;
    private String strIngredient18;
    private String strIngredient19;
    private String strIngredient20;
    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure14;
    private String strMeasure15;
    private String strMeasure16;
    private String strMeasure17;
    private String strMeasure18;
    private String strMeasure19;
    private String strMeasure20;

    protected MealEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        category = in.readString();
        area = in.readString();
        instructions = in.readString();
        thumbnail = in.readString();
        tags = in.readString();
        youtube = in.readString();
        source = in.readString();
        dateModified = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getTags() {
        return tags;
    }

    public String getSource() {
        return source;
    }

    public String getDateModified() {
        return dateModified;
    }

    public List<Ingredient> getIngredients() {
        if (ingredients == null) {
            ingredients = new ArrayList<>();

            addIngredient(strIngredient1, strMeasure1);
            addIngredient(strIngredient2, strMeasure2);
            addIngredient(strIngredient3, strMeasure3);
            addIngredient(strIngredient4, strMeasure4);
            addIngredient(strIngredient5, strMeasure5);
            addIngredient(strIngredient6, strMeasure6);
            addIngredient(strIngredient7, strMeasure7);
            addIngredient(strIngredient8, strMeasure8);
            addIngredient(strIngredient9, strMeasure9);
            addIngredient(strIngredient10, strMeasure10);
            addIngredient(strIngredient11, strMeasure11);
            addIngredient(strIngredient12, strMeasure12);
            addIngredient(strIngredient13, strMeasure13);
            addIngredient(strIngredient14, strMeasure14);
            addIngredient(strIngredient15, strMeasure15);
            addIngredient(strIngredient16, strMeasure16);
            addIngredient(strIngredient17, strMeasure17);
            addIngredient(strIngredient18, strMeasure18);
            addIngredient(strIngredient19, strMeasure19);
            addIngredient(strIngredient20, strMeasure20);
        }
        return ingredients;
    }

    private void addIngredient(String name, String quantity) {
        if (name != null && !name.isEmpty() && quantity != null && !quantity.isEmpty()) {
            ingredients.add(new Ingredient(name.trim(), quantity.trim()));
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(area);
        dest.writeString(instructions);
        dest.writeString(thumbnail);
        dest.writeString(tags);
        dest.writeString(youtube);
        dest.writeString(source);
        dest.writeString(dateModified);
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
                '}';
    }
}

