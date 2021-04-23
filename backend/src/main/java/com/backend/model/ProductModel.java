package com.backend.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.backend.entity.Category;
import com.backend.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductModel {
	@JsonProperty(access = Access.READ_ONLY)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private Long id;
	@NotNull(message = "Name is null")
	@NotBlank(message = "Name is mandatory")
	private String name;
	@NotNull(message = "Price is null")
	@Min(value = 0, message = "Price must be more than 0")
	private float price;
	private Long idCategory;
	
	public static Product modelToEntity(ProductModel model, Category category) {
		
		Product entity = new Product();
		entity.setCategory(category);
		entity.setId(model.getId());
		entity.setPrice(model.getPrice());
		entity.setName(model.getName());
		return entity;
	}
}
