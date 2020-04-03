package com.everis.bootcamp.clientms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfilesDTO {
    private Set<String> clientProfiles;
}