package com.backend.service;

import java.util.List;
import java.util.Optional;

import com.backend.entity.Category;

public interface CategoryService {
	Category createCategory(Category category);
	
	Optional<Category> getCategoryById(Long id);

	List<Category> getCategories();

	Category save(Category category);

	Category edit(Category category);

	void deleteById(Long id);
}
