package com.everis.bootcamp.clientms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CLIENT")
@EqualsAndHashCode(callSuper = false)
public class Client {
	@Id
	private String id;
	private String name;
}