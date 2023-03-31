package com.corcino.productcatalog.api;

import com.corcino.productcatalog.json.CategoryRequest;
import com.corcino.productcatalog.json.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public interface CategoryDocs {

    @Operation(description = "API to list category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned list successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication error"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<List<CategoryResponse>> getAll();


    @Operation(description = "API to find category by id")
    @Parameters(value = {
            @Parameter(name = "categoryId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category ok return"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<CategoryResponse> getById(@PathVariable Long categoryId);


    @Operation(description = "API to create a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Authentication error"),
            @ApiResponse(responseCode = "403", description = "Authorization error"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<String> create(@RequestBody @Valid CategoryRequest categoryRequest, UriComponentsBuilder uriBuilder) throws Exception;


    @Operation(description = "API to update category by categoryId")
    @Parameters(value = {
            @Parameter(name = "categoryId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "401", description = "Authentication error"),
            @ApiResponse(responseCode = "403", description = "Authorization error"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<CategoryResponse> update(@RequestBody @Valid CategoryRequest categoryRequest, @PathVariable Long categoryId) throws Exception;


    @Operation(description = "API to delete category by id")
    @Parameters(value = {
            @Parameter(name = "categoryId", in = ParameterIn.PATH)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category delete successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    ResponseEntity<Void> delete(@PathVariable Long categoryId);

}
