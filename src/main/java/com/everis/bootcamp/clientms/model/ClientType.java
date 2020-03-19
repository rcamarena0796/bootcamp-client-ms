package com.everis.bootcamp.clientms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CLIENT_TYPE")
@EqualsAndHashCode(callSuper = false)
public class ClientType {
    @Id
    private String id;
    @NotBlank(message = "'numId' is required")
    private String numId;
    private String name;
}