package com.stefan.adassignmentbe.dao;

import com.stefan.adassignmentbe.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
