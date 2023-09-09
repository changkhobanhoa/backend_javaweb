package com.vathana.ecommercespring.repository;

import com.vathana.ecommercespring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

}
