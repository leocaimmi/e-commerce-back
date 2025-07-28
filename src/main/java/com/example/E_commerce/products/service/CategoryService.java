package com.example.E_commerce.products.service;

import com.example.E_commerce.products.data.dtos.requests.CategoryRequestPostDTO;
import com.example.E_commerce.products.data.dtos.responses.CategoryResponseDTO;
import com.example.E_commerce.products.data.mappers.CategoryMapper;
import com.example.E_commerce.products.data.repositories.JpaCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper, JpaCategoryRepository jpaCategoryRepository) {
        this.categoryMapper = categoryMapper;
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    public CategoryResponseDTO createCategory(CategoryRequestPostDTO requestDTO) {
        return categoryMapper
                .toResponse(jpaCategoryRepository
                        .save(categoryMapper
                                .toEntity(requestDTO)));
    }

    public Page<CategoryResponseDTO> getAllCategories(Pageable pageable) {
        return categoryMapper
                .toPage(jpaCategoryRepository.findAll(pageable));
    }
}
