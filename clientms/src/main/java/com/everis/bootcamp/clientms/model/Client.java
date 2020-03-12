package com.everis.bootcamp.clientms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CLIENT")
@EqualsAndHashCode(callSuper = false)
public class Client {
	@Id
	private String id;
	private String name;
	private int age;
	private String numDoc;
	private String cellphone;
	private String address;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date joinDate;
}