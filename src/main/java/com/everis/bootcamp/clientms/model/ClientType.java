package com.everis.bootcamp.clientms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CLIENT_TYPE")
@EqualsAndHashCode(callSuper = false)
public class ClientType {
    private String numId;
    private int name;
}