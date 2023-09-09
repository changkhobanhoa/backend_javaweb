package com.vathana.ecommercespring.service;

import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.model.Product;
import com.vathana.ecommercespring.model.Rating;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.repository.RatingRepository;
import com.vathana.ecommercespring.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService {

    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createdRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRating(Long productId) {
        return ratingRepository.getAllProductRating(productId);
    }
}
