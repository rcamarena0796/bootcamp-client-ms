package com.everis.bootcamp.clientms.model;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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
    @NotBlank(message = "'numDoc' is required")
    private String numDoc;
    @NotBlank(message = "'bankId' is required")
    private String bankId;
    private String cellphone;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinDate;
    @NotBlank(message = "'idClientType' is required")
    private String idClientType;
}