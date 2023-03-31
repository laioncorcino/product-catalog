package com.corcino.productcatalog.json;

import com.corcino.productcatalog.model.Category;
import lombok.Data;

@Data
public class CategoryResponse {

    private Long categoryId;
    private String name;

    public CategoryResponse(Category category) {
        this.categoryId = category.getCategoryId();
        this.name = category.getName();
    }


}
