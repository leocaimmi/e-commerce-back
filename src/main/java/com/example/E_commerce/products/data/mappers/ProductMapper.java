package com.example.E_commerce.products.data.mappers;

import com.example.E_commerce.products.data.dtos.requests.ProductRequestDTO;
import com.example.E_commerce.products.data.dtos.responses.CategoryResponseDTO;
import com.example.E_commerce.products.data.dtos.responses.ProductResponseDTO;
import com.example.E_commerce.products.data.entities.CategoryEntity;
import com.example.E_commerce.products.data.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity toEntity(ProductRequestDTO requestDTO, CategoryEntity categoryEntity) {
        return ProductEntity.builder()
                .sku(requestDTO.getSku())
                .name(requestDTO.getName())
                .shortDescription(requestDTO.getShortDescription())
                .price(requestDTO.getPrice())
                .stock(requestDTO.getStock())
                .imageUrl(requestDTO.getImageUrl())
                .category(categoryEntity)
                .build();

    }

    public ProductResponseDTO toResponse(ProductEntity productEntity, CategoryResponseDTO categoryResponseDTO) {
        return ProductResponseDTO.builder()
                .id(productEntity.getId())
                .sku(productEntity.getSku())
                .name(productEntity.getName())
                .shortDescription(productEntity.getShortDescription())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .imageUrl(productEntity.getImageUrl())
                .category(categoryResponseDTO)
                .build();
    }

    public ProductResponseDTO toResponse(ProductEntity productEntity) {
        return ProductResponseDTO.builder()
                .id(productEntity.getId())
                .sku(productEntity.getSku())
                .name(productEntity.getName())
                .shortDescription(productEntity.getShortDescription())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())
                .imageUrl(productEntity.getImageUrl())
                .category(CategoryResponseDTO.builder()
                        .id(productEntity.getCategory().getId())
                        .name(productEntity.getCategory().getName())
                        .build())
                .build();
    }

    public Page<ProductResponseDTO> toPage(Page<ProductEntity> productEntityPage) {
        if (productEntityPage == null) {
            return Page.empty();
        }
        return productEntityPage.map(this::toResponse);
    }
}
