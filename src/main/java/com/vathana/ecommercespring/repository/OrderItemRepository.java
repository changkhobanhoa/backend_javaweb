package com.vathana.ecommercespring.repository;

import com.vathana.ecommercespring.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
