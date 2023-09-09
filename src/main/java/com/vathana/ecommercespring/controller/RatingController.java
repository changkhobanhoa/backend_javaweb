package com.vathana.ecommercespring.controller;

import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.Rating;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.request.RatingRequest;
import com.vathana.ecommercespring.service.RatingService;
import com.vathana.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfilesByJwt(jwt);
        Rating rating = ratingService.createdRating(req, user);
        return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRating(@PathVariable Long productId, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfilesByJwt(jwt);
        List<Rating> ratings = ratingService.getProductRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.ACCEPTED);
    }

}
