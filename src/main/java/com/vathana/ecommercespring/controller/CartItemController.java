package com.vathana.ecommercespring.controller;

import com.vathana.ecommercespring.exception.CartItemException;
import com.vathana.ecommercespring.exception.ProductException;
import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.CartItem;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.request.AddItemRequest;
import com.vathana.ecommercespring.response.ApiResponse;
import com.vathana.ecommercespring.service.CartItemService;
import com.vathana.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfilesByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Cart Item Deleted");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> addItemToCart(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfilesByJwt(jwt);
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedCartItem, HttpStatus.CREATED);
    }
}
