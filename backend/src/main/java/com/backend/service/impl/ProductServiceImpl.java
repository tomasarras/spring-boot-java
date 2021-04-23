package com.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entity.Category;
import com.backend.entity.Product;
import com.backend.model.ProductModel;
import com.backend.repository.ProductRepository;
import com.backend.service.CategoryService;
import com.backend.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product save(ProductModel product, Category category) {
		Product p = ProductModel.modelToEntity(product, category);
		return productRepository.save(p);
	}

	@Override
	public Product edit(Product productDB, ProductModel product) {
		Optional<Category> categoryDB = categoryService.getCategoryById(product.getIdCategory());
		
		if (categoryDB.isEmpty()) {
			return null;
		} else {
			productDB.setCategory(categoryDB.get());
			productDB.setName(product.getName());
			productDB.setPrice(product.getPrice());
			productRepository.save(productDB);
			return productDB;
		}
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> getProductsByIdCategory(Long idCategory) {
		return productRepository.getProductsByIdCategory(idCategory);
	}

}
