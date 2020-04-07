package com.everis.bootcamp.clientms.model;

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
@Document(collection = "CLIENT_TYPE")
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class ClientType {

  @Id
  private String id;
  @NotBlank(message = "'numId' is required")
  private String numId;
  private String name;
}