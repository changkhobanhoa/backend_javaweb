package com.vathana.ecommercespring.controller;

import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.model.Product;
import com.vathana.ecommercespring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductsByCategoryHandler(
            @RequestParam(name = "category", required = false ) String category,
            @RequestParam(name = "color", required = false) List<String> color,
            @RequestParam(name = "sizes", required = false) List<String> sizes,
            @RequestParam(name = "minPrice", required = false) Integer minPrice,
            @RequestParam(name = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(name = "minDiscount", required = false) Integer minDiscount,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "stock", required = false) String stock,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize
            ) {
        Page<Product> res = productService.getAllProduct(category, color, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);

        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProductByTitle(@RequestParam(name = "q") String search) {
        List<Product> products = productService.searchProductByTitle(search);

        return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
    }

}
