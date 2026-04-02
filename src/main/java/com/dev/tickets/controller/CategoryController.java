package com.dev.tickets.controller;

import com.dev.tickets.dto.category.CategoryRequest;
import com.dev.tickets.dto.category.CategoryResponse;
import com.dev.tickets.dto.event.EventResponse;
import com.dev.tickets.mapper.CategoryMapper;
import com.dev.tickets.mapper.EventMapper;
import com.dev.tickets.model.CategoryEntity;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request){
        CategoryEntity category = categoryService.createCategory(request);
        CategoryResponse res = CategoryMapper.toResponse(category);
        return ResponseEntity.ok().body(res);
    }


    @GetMapping
    public ResponseEntity<?> getCategories(){
        List<CategoryEntity> categories = categoryService.getAllCategories();
        List<CategoryResponse> res = categories.stream().map( CategoryMapper::toResponse ).toList();
        return ResponseEntity.ok().body(res);
    }

}
