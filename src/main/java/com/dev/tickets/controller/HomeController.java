package com.dev.tickets.controller;

import com.dev.tickets.dto.HomeResponse;
import com.dev.tickets.dto.category.CategoryResponse;
import com.dev.tickets.mapper.CategoryMapper;
import com.dev.tickets.model.CategoryEntity;
import com.dev.tickets.service.CategoryService;
import com.dev.tickets.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;
    private final CategoryService categoryService;

    public HomeController(HomeService homeService, CategoryService categoryService) {
        this.homeService = homeService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> homeData(){
        HomeResponse response = homeService.getHomeData();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<?> getCategoryData(@PathVariable(name = "category") String name){
        CategoryEntity category = categoryService.getByName(name);
        CategoryResponse res = CategoryMapper.toResponse(category);
        return ResponseEntity.ok().body(res);
    }

}
