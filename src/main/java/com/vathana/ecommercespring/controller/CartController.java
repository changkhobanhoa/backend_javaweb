package com.vathana.ecommercespring.controller;

import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.Cart;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.request.AddItemRequest;
import com.vathana.ecommercespring.response.ApiResponse;
import com.vathana.ecommercespring.service.CartService;
import com.vathana.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfilesByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfilesByJwt(jwt);
        cartService.addCartItem(user.getId(), req);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
