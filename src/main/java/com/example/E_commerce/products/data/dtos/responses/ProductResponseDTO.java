package com.example.E_commerce.products.data.dtos.responses;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponseDTO {

    private UUID id;
    private String name;
    private String sku;
    private String shortDescription;
    private String imageUrl;
    private BigDecimal price;
    private Integer stock;
    private CategoryResponseDTO category;




}
