package com.vathana.ecommercespring.repository;

import com.vathana.ecommercespring.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r where r.product.id = :productId")
    public List<Rating> getAllProductRating(@Param("productId") Long productId);

}
