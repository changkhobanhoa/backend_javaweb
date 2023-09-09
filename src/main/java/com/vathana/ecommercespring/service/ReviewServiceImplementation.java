package com.vathana.ecommercespring.service;

import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.model.Product;
import com.vathana.ecommercespring.model.Rating;
import com.vathana.ecommercespring.model.Review;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.repository.ProductRepository;
import com.vathana.ecommercespring.repository.RatingRepository;
import com.vathana.ecommercespring.repository.ReviewRepository;
import com.vathana.ecommercespring.request.RatingRequest;
import com.vathana.ecommercespring.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductService productService, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setCreatedAt(LocalDateTime.now());
        review.setReview(req.getReview());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductReview(productId);
    }
}
