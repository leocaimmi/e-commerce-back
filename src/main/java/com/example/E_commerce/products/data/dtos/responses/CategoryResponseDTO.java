package com.example.E_commerce.products.data.dtos.responses;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponseDTO {
    private UUID id;
    private String name;
}
