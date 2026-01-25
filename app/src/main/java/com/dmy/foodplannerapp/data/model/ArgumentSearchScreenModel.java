package com.dmy.foodplannerapp.data.model;

import java.io.Serializable;

enum SearchType {
    MEAL,
    INGREDIENT,
    CATEGORY,
    COUNTRY
}

public class ArgumentSearchScreenModel implements Serializable {
    private SearchType type;
    private String name;

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
}

