package com.everis.bootcamp.clientms.service.impl;

import com.everis.bootcamp.clientms.dto.ClientProfilesDto;
import com.everis.bootcamp.clientms.service.ExternalCallService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalCallServiceImpl implements ExternalCallService {

  @Override
  public Mono<Boolean> getExistBank(String numId) {
    String url = "http://localhost:8002/bank/exist/" + numId;
    return WebClient.create()
        .get()
        .uri(url)
        .retrieve()
        .bodyToMono(Boolean.class);
  }

  @Override
  public Mono<Boolean> verifyProfile(String bankId, String profileId) {
    String url = "http://localhost:8002/bank/bankProfiles/" + bankId;
    Mono<ClientProfilesDto> profilesDto = WebClient.create()
        .get()
        .uri(url)
        .retrieve()
        .bodyToMono(ClientProfilesDto.class);

    return profilesDto.map(p -> {
      if (p.getClientProfiles().contains(profileId)) {
        return true;
      } else {
        return false;
      }
    });
  }
}
