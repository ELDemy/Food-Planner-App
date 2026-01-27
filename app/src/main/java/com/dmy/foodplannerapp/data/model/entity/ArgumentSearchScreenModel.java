package com.dmy.foodplannerapp.data.model.entity;
//noinspection unused

import java.io.Serializable;

public class ArgumentSearchScreenModel implements Serializable {
    public String name;
    public SearchType type;

    public ArgumentSearchScreenModel(SearchType type, String name) {
        this.name = name;
        this.type = type;
    }

    public SearchType getType() {
        return type;
    }

    public void setType(SearchType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public enum SearchType {
        MEAL,
        INGREDIENT,
        CATEGORY,
        COUNTRY
    }


}

