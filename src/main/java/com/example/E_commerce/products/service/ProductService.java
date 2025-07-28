package com.example.E_commerce.products.service;

import com.example.E_commerce.products.data.dtos.requests.ProductRequestDTO;
import com.example.E_commerce.products.data.dtos.responses.ProductResponseDTO;
import com.example.E_commerce.products.data.entities.CategoryEntity;
import com.example.E_commerce.products.data.entities.ProductEntity;
import com.example.E_commerce.products.data.mappers.CategoryMapper;
import com.example.E_commerce.products.data.mappers.ProductMapper;
import com.example.E_commerce.products.data.repositories.JpaCategoryRepository;
import com.example.E_commerce.products.data.repositories.JpaProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final JpaProductRepository jpaProductRepository;
    private final JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    public ProductService(CategoryMapper categoryMapper, ProductMapper productMapper, JpaProductRepository jpaProductRepository, JpaCategoryRepository jpaCategoryRepository) {
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
        this.jpaProductRepository = jpaProductRepository;
        this.jpaCategoryRepository = jpaCategoryRepository;
    }


    public Page<ProductResponseDTO> getAllProducts(Pageable pageable){
        return productMapper.toPage(jpaProductRepository.findAll(pageable));
    }

    /**
     * Creates a new product based on the provided request DTO.
     *
     * @param requestDTO The DTO containing the product details.
     * @return The created ProductResponseDTO.
     */
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        CategoryEntity category = jpaCategoryRepository.findById(requestDTO.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        ProductEntity newProduct = jpaProductRepository
                .save(productMapper.toEntity(requestDTO,category));

        return productMapper.toResponse(newProduct, categoryMapper.toResponse(category));
    }
}
