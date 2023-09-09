package com.vathana.ecommercespring.controller;

import com.vathana.ecommercespring.exception.OrderException;
import com.vathana.ecommercespring.exception.UserException;
import com.vathana.ecommercespring.model.Order;
import com.vathana.ecommercespring.model.User;
import com.vathana.ecommercespring.response.ApiResponse;
import com.vathana.ecommercespring.service.OrderService;
import com.vathana.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }

        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelledOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }
        Order order = orderService.cancelledOrder(orderId);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deletedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfilesByJwt(jwt);

        if (!user.getRole().equalsIgnoreCase("admin")) {
            throw new UserException("Only for admin access");
        }
        orderService.deleteOrder(orderId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Order deleted successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
