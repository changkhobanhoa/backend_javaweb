package com.vathana.ecommercespring.service;

import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.model.Rating;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createdRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductRating(Long productId);
}
