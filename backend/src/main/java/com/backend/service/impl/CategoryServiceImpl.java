package com.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.entity.Category;
import com.backend.repository.CategoryRepository;
import com.backend.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Optional<Category> getCategoryById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category save(Category category) {
		category.setId(null);
		return categoryRepository.save(category);
	}

	@Override
	public Category edit(Category category) {
		Optional<Category> categoryDB = this.getCategoryById(category.getId());
		if (categoryDB.isEmpty()) {
			return null;
		} else {
			Category categoryEdited = categoryDB.get();
			categoryEdited.setName(category.getName());
			categoryEdited = categoryRepository.save(categoryEdited);
			return categoryEdited;
		}
	}

	@Override
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

}
