package com.dev.tickets.mapper;

import com.dev.tickets.dto.category.CategoryResponse;
import com.dev.tickets.model.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public static CategoryResponse toResponse(CategoryEntity entity){
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .build();
    }

}
