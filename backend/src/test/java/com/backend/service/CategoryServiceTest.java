package com.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.backend.entity.Category;
import com.backend.repository.CategoryRepository;

@SpringBootTest
public class CategoryServiceTest {
	@MockBean
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Test
	public void testCreateCategory() {
		Category categoryToSave = new Category();
		categoryToSave.setName("test");
		Category categoryReturn = new Category();
		categoryReturn.setId(1L);
		categoryReturn.setName("test");
		
		when(categoryRepository.save(categoryToSave)).thenReturn(categoryReturn);
		Category c = categoryService.save(categoryToSave);
		assertEquals(categoryReturn.getId(), c.getId());
		assertEquals(categoryReturn.getName(), c.getName());
		
	}
	
	@Test
	public void testGetCategoryById() {
		Category categoryDB = new Category();
		categoryDB.setId(1L);
		categoryDB.setName("test");
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryDB));
		Optional<Category> categoryFound = categoryService.getCategoryById(1L);
		
		assertEquals(false, categoryFound.isEmpty());
		assertEquals(categoryDB.getId(), categoryFound.get().getId());
		assertEquals(categoryDB.getName(), categoryFound.get().getName());
	}
	
	@Test
	public void testGetCategories() {
		Category c1 = new Category();
		Category c2 = new Category();
		c1.setId(1L);
		c2.setId(2L);
		c2.setName("test1");
		c2.setName("test2");
		List<Category> listCategories = new ArrayList<Category>();
		listCategories.add(c1);
		listCategories.add(c2);
		
		when(categoryRepository.findAll()).thenReturn(listCategories);
		
		List<Category> categoriesFound = categoryService.getCategories();
		
		assertEquals(2,categoriesFound.size());
		assertEquals(true, categoriesFound.containsAll(listCategories));
	}
	
	@Test
	public void testSave() {
		Category category = new Category();
		category.setName("test");
		Category category2 = new Category();
		category.setName("test");
		category.setId(1L);
		
		when(categoryRepository.save(category)).thenReturn(category2);
		
		Category categorySaved = categoryService.save(category);
		
		assertEquals(category2.getId(), categorySaved.getId());
		assertEquals(category2.getName(), categorySaved.getName());
	}
	
	@Test
	public void testEdit() {
		Category categoryDB = new Category();
		categoryDB.setId(1L);
		categoryDB.setName("test");
		
		Category catogoryParam = new Category();
		catogoryParam.setId(categoryDB.getId());
		catogoryParam.setName("testEdited");
		
		Category categoryEdited = new Category();
		categoryEdited.setId(categoryDB.getId());
		categoryEdited.setName("testEdited");
		
		doReturn(Optional.of(categoryDB)).when(categoryRepository).findById(categoryDB.getId());
		doReturn(categoryEdited).when(categoryRepository).save(Mockito.any(Category.class));
		
		Category categoryReturn = categoryService.save(catogoryParam);
		
		assertEquals(categoryEdited.getId(),categoryReturn.getId());
		assertEquals(categoryEdited.getName(),categoryReturn.getName());
	}
	
	@Test
	public void testEditNotFound() {
		Category c = new Category();
		c.setId(1L);
		c.setName("test");
		when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertNull(categoryService.edit(c));
	}
	
	
}