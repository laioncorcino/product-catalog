package com.corcino.productcatalog.service;

import com.corcino.productcatalog.error.exception.NotFoundException;
import com.corcino.productcatalog.json.CategoryRequest;
import com.corcino.productcatalog.json.CategoryResponse;
import com.corcino.productcatalog.model.Category;
import com.corcino.productcatalog.repository.CategoryRepository;
import io.micrometer.common.util.StringUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@NoArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = getById(categoryId);
        return new CategoryResponse(category);
    }

    private Category getById(Long categoryId) {
        log.info("Buscando categoria de id {}", categoryId);
        Optional<Category> category = categoryRepository.findById(categoryId);

        return category.orElseThrow(() -> {
            log.error("Favorito de id {} nao encontrado", categoryId);
            return new NotFoundException("Favorito nao encontrado");
        });
    }

    @Transactional
    public Category createCategory(CategoryRequest categoryRequest) throws Exception {
        Category category = categoryRequest.toModel();
        log.info("Salvando categoria");
        return saveCategory(category);
    }

    private Category saveCategory(Category category) throws Exception {
        try {
            return categoryRepository.save(category);
        }
        catch (DuplicateKeyException e) {
            log.error("Categoria com nome ja existente");
            throw new DuplicateKeyException("Category name duplicated");
        }
        catch (Exception | Error e) {
            log.error("Erro ao salvar categoria", e.getCause());
            throw new Exception(e.getCause());
        }
    }

    public CategoryResponse update(CategoryRequest categoryRequest, Long categoryId) throws Exception {
        Category category = getById(categoryId);

        if (StringUtils.isNotBlank(categoryRequest.getName())) {
            category.setName(categoryRequest.getName());
        }

        log.info("Atualizando categoria " + category.getCategoryId());

        Category savedCategory = saveCategory(category);
        return new CategoryResponse(savedCategory);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        getById(categoryId);
        log.info("Deletando categoria de id {}", categoryId);
        categoryRepository.deleteById(categoryId);
    }

}


