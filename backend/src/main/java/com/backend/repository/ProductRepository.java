package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Category c JOIN c.products p WHERE c.id = :idCategory")
	List<Product> getProductsByIdCategory(Long idCategory);
	
}
