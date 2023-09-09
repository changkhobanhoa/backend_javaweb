package com.vathana.ecommercespring.service;

import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.model.Category;
import com.vathana.ecommercespring.model.Product;
import com.vathana.ecommercespring.repository.CategoryRepository;
import com.vathana.ecommercespring.repository.ProductRepository;
import com.vathana.ecommercespring.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImplementation(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountedPercent(req.getDiscountedPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSizes());
        product.setQuantity(req.getQuantity());
        product.setCreatedAt(LocalDateTime.now());

        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }

        product.setCategory(topLevel);

        if (req.getSecondLevelCategory() != null) {
            Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());

            if (secondLevel == null) {
                Category secondLevelCategory = new Category();
                secondLevelCategory.setName(req.getSecondLevelCategory());
                secondLevelCategory.setParentCategory(topLevel);
                secondLevelCategory.setLevel(2);

                secondLevel = categoryRepository.save(secondLevelCategory);
            }

            product.setCategory(secondLevel);

        }

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);

        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);

        // if (req.getQuantity() != 0) {
        //    product.setQuantity(req.getQuantity());
        // }
        product = req;

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt = productRepository.findById(productId);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new ProductException("Product not found with id - " + productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) throws ProductException {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscountPrice, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscountPrice, sort);

        if (colors != null && !colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(
               c -> c.equalsIgnoreCase(p.getColor())
            )).collect(Collectors.toList());
        }

        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);

        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());

        return filteredProducts;
    }

    @Override
    public List<Product> searchProductByTitle(String search) {
        List<Product> products = productRepository.findByTitleContainingIgnoreCase(search);

        return products;
    }


}
