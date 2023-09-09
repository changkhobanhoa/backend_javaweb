package com.vathana.ecommercespring.service;

import com.vathana.ecommercespring.exception.CartItemException;
import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.Cart;
import com.vathana.ecommercespring.model.CartItem;
import com.vathana.ecommercespring.model.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;


}
