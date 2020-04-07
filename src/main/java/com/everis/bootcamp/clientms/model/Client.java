package com.everis.bootcamp.clientms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CLIENT")
@Builder(toBuilder = true)
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