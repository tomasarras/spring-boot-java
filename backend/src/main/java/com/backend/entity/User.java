package com.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue
	@Column(name = "id_user")
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@Column
	@NotNull(message = "username is null")
	@NotBlank(message = "username is mandatory")
	private String username;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	@Column
	@NotNull(message = "password is null")
	@NotBlank(message = "password is mandatory")
	private String password;
	@Column
	@JsonProperty(access = Access.READ_ONLY)
	@Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
	private boolean admin;
	
}
