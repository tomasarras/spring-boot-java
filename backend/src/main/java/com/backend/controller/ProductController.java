package com.backend.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.entity.Category;
import com.backend.entity.Product;
import com.backend.model.ProductModel;
import com.backend.service.CategoryService;
import com.backend.service.LogService;
import com.backend.service.ProductService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/categories")
public class ProductController {
	@Autowired
	@Qualifier("productService")
	private ProductService productService;
	
	@Autowired
	@Qualifier("logService")
	private LogService logService;
	
	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;
	
	@ApiOperation(value = "Get all products")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK")
	})
	@GetMapping("/products")
	public List<Product> getProducts() {
		logService.info(this.getClass(), "METHOD: 'getProducts()'");
		return productService.getProducts();
	}
	
	
	
	
	
	@ApiOperation(value = "Get product by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id product", dataTypeClass = Long.class, required = true)
	})
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		logService.info(this.getClass(), "METHOD: 'getProductById()' PARAM id=" + id);
		Optional<Product> productDB = productService.getProductById(id);
		
		if (productDB.isEmpty())
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		else
			return ResponseEntity.status(Response.SC_OK).body(productDB.get());
	}
	
	
	
	
	@ApiOperation(value = "Get all products of one category")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "id category not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "idCategory", value = "id category of products", dataTypeClass = Long.class, required = true)
	})
	@GetMapping("/{idCategory}/products")
	public ResponseEntity<List<Product>> getProductsByIdCategory(@PathVariable Long idCategory) {
		logService.info(this.getClass(), "METHOD: 'getProductsByIdCategory()' PARAM idCategory=" + idCategory);
		Optional<Category> categoryDB = categoryService.getCategoryById(idCategory);
		if (categoryDB.isEmpty())
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		else
			return ResponseEntity.status(Response.SC_OK).body(productService.getProductsByIdCategory(idCategory));
	}
	
	
	
	
	@ApiOperation(value = "Create product")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 404, message = "Category not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "idCategory", value = "id category of the product", dataTypeClass = Long.class,required = true)
	})
	@PostMapping("/{idCategory}/products")
	public ResponseEntity<Product> saveProduct(@Valid @RequestBody ProductModel productBody, @PathVariable Long idCategory) {
		logService.info(this.getClass(), "METHOD: 'saveProduct()' PARAMS productBody=" + productBody + " - idCategory=" + idCategory);
		Optional<Category> categoryDB = categoryService.getCategoryById(idCategory);
		if (categoryDB.isEmpty()) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		} else {
			return ResponseEntity.status(Response.SC_CREATED).body(productService.save(productBody,categoryDB.get()));
		}
	}
	
	
	
	
	@ApiOperation(value = "Edit product")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Product or category not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id product", dataTypeClass = Long.class, required = true)
	})
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> editProduct(@PathVariable Long id,@Valid @RequestBody ProductModel productBody) {
		logService.info(this.getClass(), "METHOD: 'editProduct()' PARAMS id=" + id + " - ProductModel=" + productBody);
		productBody.setId(id);
		Optional<Product> productDB = productService.getProductById(id);
		
		if (productDB.isEmpty()) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		} else {
			Product productEdited =  productService.edit(productDB.get(), productBody);
			if (productEdited == null)
				return ResponseEntity.status(Response.SC_NOT_FOUND).build();
			else
				return ResponseEntity.status(Response.SC_OK).body(productEdited);
		}
	}
	
	
	
	
	@ApiOperation(value = "Delete product")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id product", dataTypeClass = Long.class, required = true)
	})
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		logService.info(this.getClass(), "METHOD: 'deleteProduct()' PARAM id=" + id);
		boolean existsProduct = productService.getProductById(id).isPresent();
		if (existsProduct) {
			productService.deleteById(id);
			return ResponseEntity.status(Response.SC_NO_CONTENT).build();
		} else {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
	}
}
