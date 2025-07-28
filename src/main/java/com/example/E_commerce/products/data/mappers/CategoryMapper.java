package com.example.E_commerce.products.data.mappers;

import com.example.E_commerce.products.data.dtos.requests.CategoryRequestPostDTO;
import com.example.E_commerce.products.data.dtos.responses.CategoryResponseDTO;
import com.example.E_commerce.products.data.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {


    public CategoryEntity toEntity(CategoryRequestPostDTO categoryResponseDTO) {
        return CategoryEntity.builder()
                .name(categoryResponseDTO.getName())
                .build();
    }

    public CategoryResponseDTO toResponse(CategoryEntity category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();

    }

    public Page<CategoryResponseDTO> toPage(Page<CategoryEntity> categories) {
        if (categories == null) {
            return Page.empty();
        }
        return categories.map(this::toResponse);
    }

}
