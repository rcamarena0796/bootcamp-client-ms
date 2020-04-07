package com.everis.bootcamp.clientms.service;

import reactor.core.publisher.Mono;

public interface ExternalCallService {

  public Mono<Boolean> getExistBank(String numId);

  public Mono<Boolean> verifyProfile(String bankId, String profileId);
}
