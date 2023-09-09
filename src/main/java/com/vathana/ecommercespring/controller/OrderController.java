package com.vathana.ecommercespring.controller;

import com.vathana.ecommercespring.exception.OrderException;
import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.Address;
import com.vathana.ecommercespring.model.Order;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.service.OrderService;
import com.vathana.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfilesByJwt(jwt);
        Order order = orderService.createOrder(user, shippingAddress);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfilesByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user = userService.findUserProfilesByJwt(jwt);
        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

}
