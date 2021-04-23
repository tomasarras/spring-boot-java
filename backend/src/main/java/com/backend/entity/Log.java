package com.backend.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Log {
	@Id
	@GeneratedValue
	@Column(name = "id_log")
	private Long id;
	@Column
	private Timestamp date;
	@Column
	private String message;
	@Column(name = "class")
	private String className;
}
