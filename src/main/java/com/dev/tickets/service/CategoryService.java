package com.dev.tickets.service;

import com.dev.tickets.dto.category.CategoryRequest;
import com.dev.tickets.exception.AppException;
import com.dev.tickets.model.CategoryEntity;
import com.dev.tickets.model.EventEntity;
import com.dev.tickets.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryEntity createCategory(CategoryRequest request){
        String name = request.name().trim().toLowerCase();
        Optional<CategoryEntity> existing = categoryRepository.findByName(name);
        if( existing.isPresent() ){
            throw new AppException("Ya existe una categoria con ese nombre");
        }
        // TODO: save image to cloudinary
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setImage(request.image());
        return categoryRepository.save(category);
    }

    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }

    public List<EventEntity> getEventsByCategory(String name){
        CategoryEntity category = categoryRepository.findByName(name)
                .orElseThrow( () -> new AppException("Category not found") );
        return category.getEvents();
    }

    public CategoryEntity getByName(String name){
        return categoryRepository.findByName(name)
                .orElseThrow( () -> new AppException("Category not found") );
    }

}
