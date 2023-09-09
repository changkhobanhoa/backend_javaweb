package com.vathana.ecommercespring.repository;

import com.vathana.ecommercespring.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
