package com.example.E_commerce.products.data.dtos.requests;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductRequestDTO {

    private String sku;
    private String name;
    private String shortDescription;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private CategoryRequestDTO category;

}
