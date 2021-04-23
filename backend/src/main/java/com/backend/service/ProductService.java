package com.backend.service;

import java.util.List;
import java.util.Optional;

import com.backend.entity.Category;
import com.backend.entity.Product;
import com.backend.model.ProductModel;

public interface ProductService {
	List<Product> getProducts();

	Optional<Product> getProductById(Long id);

	Product save(ProductModel product, Category category);
	
	Product edit(Product productDB ,ProductModel product);

	void deleteById(Long id);

	List<Product> getProductsByIdCategory(Long idCategory);
}
