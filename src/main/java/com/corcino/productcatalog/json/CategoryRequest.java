package com.corcino.productcatalog.json;


import com.corcino.productcatalog.model.Category;
import lombok.Getter;

@Getter
public class CategoryRequest {

    private String name;

    public Category toModel() {
        return new Category(name);
    }

}
