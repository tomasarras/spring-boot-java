package com.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.entity.Category;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CategoryRepositoryTest {
	@Autowired
	CategoryRepository categoryRepository;
	
	
	@Test
	@Order(1)
	public void testCreateCategory() {
		Category category = new Category();
		category.setName("test");
		Category categoryCreated = categoryRepository.save(category);
		Category foundCategory = categoryRepository.findById(categoryCreated.getId()).get();
        assertNotNull(foundCategory);
        assertEquals(foundCategory.getName(), categoryCreated.getName());
	}
	
	@Test
	@Order(2)
	public void testFindAll() {
		Category c = new Category();
		c.setName("test2");
		categoryRepository.save(c);
		
		List<Category> categories = categoryRepository.findAll();
		assertEquals(2,categories.size());
	}
	
	@Test
	@Order(3)
	public void testDeleteById() {
		Category c = new Category();
		c.setName("test2");
		Category categoryDB = categoryRepository.save(c);
		
		categoryRepository.deleteById(categoryDB.getId());
		boolean isDeleted = categoryRepository.findById(categoryDB.getId()).isEmpty();
		assertEquals(true, isDeleted);
	}

}
