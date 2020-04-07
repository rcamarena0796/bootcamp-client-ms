package com.everis.bootcamp.clientms.service;

import com.everis.bootcamp.clientms.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

  public Mono<Client> findByName(String name);

  public Mono<Client> findById(String id);

  public Flux<Client> findAll();

  public Mono<Client> findByNumDoc(String numDoc);

  public Mono<Client> update(Client c, String id);

  public Mono<Void> delete(String id);

  public Mono<Client> save(Client cl);

  public Mono<Boolean> existsByNumDoc(String numDoc);

  public Mono<String> getClientTypeByNumDoc(String numDoc);

}
