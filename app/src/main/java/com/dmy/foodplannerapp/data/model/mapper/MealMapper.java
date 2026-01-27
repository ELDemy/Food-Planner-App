package com.dmy.foodplannerapp.data.model.mapper;

import com.dmy.foodplannerapp.data.model.dto.MealDto;
import com.dmy.foodplannerapp.data.model.entity.MealEntity;

import java.util.ArrayList;
import java.util.List;

public final class MealMapper {

    private MealMapper() {
    }

    public static MealEntity toEntity(MealDto dto) {
        if (dto == null)
            return null;

        MealEntity entity = new MealEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setArea(dto.getArea());
        entity.setInstructions(dto.getInstructions());
        entity.setThumbnail(dto.getThumbnail());
        entity.setTags(dto.getTags());
        entity.setYoutube(dto.getYoutube());
        entity.setSource(dto.getSource());
        entity.setDateModified(dto.getDateModified());
        entity.setFavourite(false);

        entity.setStrIngredient1(dto.getStrIngredient1());
        entity.setStrIngredient2(dto.getStrIngredient2());
        entity.setStrIngredient3(dto.getStrIngredient3());
        entity.setStrIngredient4(dto.getStrIngredient4());
        entity.setStrIngredient5(dto.getStrIngredient5());
        entity.setStrIngredient6(dto.getStrIngredient6());
        entity.setStrIngredient7(dto.getStrIngredient7());
        entity.setStrIngredient8(dto.getStrIngredient8());
        entity.setStrIngredient9(dto.getStrIngredient9());
        entity.setStrIngredient10(dto.getStrIngredient10());
        entity.setStrIngredient11(dto.getStrIngredient11());
        entity.setStrIngredient12(dto.getStrIngredient12());
        entity.setStrIngredient13(dto.getStrIngredient13());
        entity.setStrIngredient14(dto.getStrIngredient14());
        entity.setStrIngredient15(dto.getStrIngredient15());
        entity.setStrIngredient16(dto.getStrIngredient16());
        entity.setStrIngredient17(dto.getStrIngredient17());
        entity.setStrIngredient18(dto.getStrIngredient18());
        entity.setStrIngredient19(dto.getStrIngredient19());
        entity.setStrIngredient20(dto.getStrIngredient20());

        entity.setStrMeasure1(dto.getStrMeasure1());
        entity.setStrMeasure2(dto.getStrMeasure2());
        entity.setStrMeasure3(dto.getStrMeasure3());
        entity.setStrMeasure4(dto.getStrMeasure4());
        entity.setStrMeasure5(dto.getStrMeasure5());
        entity.setStrMeasure6(dto.getStrMeasure6());
        entity.setStrMeasure7(dto.getStrMeasure7());
        entity.setStrMeasure8(dto.getStrMeasure8());
        entity.setStrMeasure9(dto.getStrMeasure9());
        entity.setStrMeasure10(dto.getStrMeasure10());
        entity.setStrMeasure11(dto.getStrMeasure11());
        entity.setStrMeasure12(dto.getStrMeasure12());
        entity.setStrMeasure13(dto.getStrMeasure13());
        entity.setStrMeasure14(dto.getStrMeasure14());
        entity.setStrMeasure15(dto.getStrMeasure15());
        entity.setStrMeasure16(dto.getStrMeasure16());
        entity.setStrMeasure17(dto.getStrMeasure17());
        entity.setStrMeasure18(dto.getStrMeasure18());
        entity.setStrMeasure19(dto.getStrMeasure19());
        entity.setStrMeasure20(dto.getStrMeasure20());

        return entity;
    }

    public static List<MealEntity> toEntityList(List<MealDto> dtos) {
        if (dtos == null)
            return new ArrayList<>();

        List<MealEntity> entities = new ArrayList<>();
        for (MealDto dto : dtos) {
            entities.add(toEntity(dto));
        }
        return entities;
    }
}
