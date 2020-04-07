package com.everis.bootcamp.clientms.service;

import com.everis.bootcamp.clientms.model.ClientType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientTypeService {

  public Flux<ClientType> findAll();

  public Mono<ClientType> findByNumId(String numId);

  public Mono<ClientType> update(ClientType c, String numId);

  public Mono<Void> delete(String id);

  public Mono<ClientType> save(ClientType cl);
}
