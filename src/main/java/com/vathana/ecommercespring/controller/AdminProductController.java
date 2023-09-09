package com.vathana.ecommercespring.controller;

import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.Product;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.request.CreateProductRequest;
import com.vathana.ecommercespring.response.ApiResponse;
import com.vathana.ecommercespring.service.ProductService;
import com.vathana.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestHeader("Authorization") String jwt, @RequestBody CreateProductRequest req) throws UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }

        Product product = productService.createProduct(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestHeader("Authorization") String jwt, @PathVariable Long productId) throws ProductException, UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }

        productService.deleteProduct(productId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Product deleted successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // @GetMapping("/all")
    // public ResponseEntity<List<Product>> findAllProducts(){
    //    productService.findAllProducts();

    //    return new ResponseEntity<>(res, HttpStatus.OK);
    //}

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestHeader("Authorization") String jwt, @RequestBody Product req, @PathVariable Long productId) throws ProductException, UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }

        Product product = productService.updateProduct(productId, req);

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PutMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProducts(@RequestHeader("Authorization") String jwt, @RequestBody CreateProductRequest[] req) throws UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }

        for (CreateProductRequest product : req) {
            productService.createProduct(product);
        }

        ApiResponse res = new ApiResponse();
        res.setMessage("Product created successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



}
