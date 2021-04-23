package com.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.backend.entity.Category;
import com.backend.entity.Product;
import com.backend.model.ProductModel;
import com.backend.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@Test
	public void testGetProducts() {
		Category c1 = new Category();
		Category c2 = new Category();
		c1.setId(1L);
		c2.setId(2L);
		c1.setName("c1");
		c2.setName("c2");
		Product p1 = new Product();
		Product p2 = new Product();
		p1.setCategory(c1);
		p2.setCategory(c2);
		p1.setId(1L);
		p2.setId(2L);
		p1.setName("p1");
		p2.setName("p2");
		p1.setPrice(2);
		p2.setPrice(3);
		List<Product> products = new LinkedList<Product>();
		products.add(p1);
		products.add(p2);
		when(productRepository.findAll()).thenReturn(products);
		List<Product> productsReturn = productService.getProducts();
		assertEquals(true, productsReturn.containsAll(products));
	}
	
	@Test
	public void testGetProductById() {
		Category c1 = new Category();
		c1.setId(1L);
		c1.setName("c1");
		Product p1 = new Product();
		p1.setCategory(c1);
		p1.setId(1L);
		p1.setName("p1");
		p1.setPrice(1);
		when(productRepository.findById(p1.getId())).thenReturn(Optional.of(p1));
		Optional<Product> productFound = productService.getProductById(p1.getId());
		
		assertEquals(false, productFound.isEmpty());
		assertEquals(true, p1.equals(productFound.get()));
	}
	
	@Test
	public void testSave() {
		ProductModel p1 = new ProductModel();
		Category c1 = new Category();
		c1.setId(1L);
		c1.setName("c1");
		p1.setName("p1");
		p1.setPrice(1);
		
		Product p2 = new Product();
		p2.setId(1L);
		p2.setName("p1");
		p2.setCategory(c1);
		p2.setPrice(1);
		when(productRepository.save(Mockito.any(Product.class))).thenReturn(p2);
		
		Product productSaved = productService.save(p1, c1);
		assertEquals(true, p2.equals(productSaved));
	}
	
	@Test
	public void testEdit() {
		
		Category c1 = new Category();
		c1.setId(1L);
		c1.setName("c1");
		
		Product productDB = new Product();
		productDB.setCategory(c1);
		productDB.setId(1L);
		productDB.setName("p1");
		productDB.setPrice(1);
		
		ProductModel productToEdit = new ProductModel();
		productToEdit.setId(productDB.getId());
		productToEdit.setIdCategory(c1.getId());;
		productToEdit.setName("p1Edited");;
		productToEdit.setPrice(2);
		
		Product productExpected = new Product();
		productExpected.setCategory(c1);
		productExpected.setPrice(productToEdit.getPrice());
		productExpected.setId(productToEdit.getId());
		productExpected.setName(productToEdit.getName());
		
		doReturn(Optional.of(c1)).when(categoryService).getCategoryById(c1.getId());
		doReturn(productExpected).when(productRepository).save(productDB);
		
		Product productEdited = productService.edit(productDB,productToEdit);
		assertEquals(true, productEdited.equals(productExpected));
		
	}
	
	@Test
	public void testEditProductWithoutCategory() {
		ProductModel pm = new ProductModel();
		Category c1 = new Category();
		c1.setId(1L);
		c1.setName("c1");
		pm.setIdCategory(1L);
		Product p1 = new Product();
		p1.setId(1L);
		p1.setName("p1");
		p1.setPrice(1);
		p1.setCategory(c1);
		doReturn(Optional.empty()).when(categoryService).getCategoryById(pm.getIdCategory());
		Product response = productService.edit(p1, pm);
		assertNull(response);
	}
}
