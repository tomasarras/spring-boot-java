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
import com.backend.service.CategoryService;
import com.backend.service.LogService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;
	
	@Autowired
	@Qualifier("logService")
	private LogService logService;
	
	
	@ApiOperation(value = "Get all categories")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
	})
	@GetMapping
	public List<Category> getCategories() {
		logService.info(this.getClass(), "METHOD: 'getCategories()'");
		return categoryService.getCategories();
	}
	
	
	
	
	
	@ApiOperation(value = "Get category by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id category", dataTypeClass = Long.class, required = true)
	})
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
		logService.info(this.getClass(), "METHOD: 'getCategoryById()' PARAM id=" + id);
		Optional<Category> categoryDB = categoryService.getCategoryById(id);
		if (categoryDB.isEmpty())
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		else
			return ResponseEntity.status(Response.SC_OK).body(categoryDB.get());
	}
	
	
	
	
	
	@ApiOperation(value = "Create category")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	})
	@PostMapping
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category categoryBody) {
		logService.info(this.getClass(), "METHOD: 'createCategory()' PARAM categoryBody=" + categoryBody);
		return ResponseEntity.status(Response.SC_CREATED).body(categoryService.save(categoryBody));
	}
	
	
	
	
	
	@ApiOperation(value = "Edit category")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id category", dataTypeClass = Long.class, required = true)
	})
	@PutMapping("/{id}")
	public ResponseEntity<Category> editCategory(@PathVariable Long id,@Valid @RequestBody Category categoryBody) {
		logService.info(this.getClass(), "METHOD: 'editCategory()' PARAMS id=" + id + " - categoryBody=" + categoryBody);
		categoryBody.setId(id);
		Category categoryEdited = categoryService.edit(categoryBody);
		if (categoryEdited == null) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		} else {
			return ResponseEntity.status(Response.SC_OK).body(categoryEdited);
		}
	}
	
	
	
	
	
	@ApiOperation(value = "Delete category")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Deleted, no content"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id category", dataTypeClass = Long.class,required = true)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		logService.info(this.getClass(), "METHOD: 'deleteCategory()' PARAM id=" + id);
		boolean existsCategory = categoryService.getCategoryById(id).isPresent();
		if (existsCategory) {
			categoryService.deleteById(id);
			return ResponseEntity.status(Response.SC_NO_CONTENT).build();
		} else {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
	}
	
}
