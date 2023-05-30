package com.corcino.productcatalog.json;


import com.corcino.productcatalog.model.Category;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryRequest {

    @NotNull
    private String name;

    public Category toModel() {
        return new Category(name);
    }

}
