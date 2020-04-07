package com.everis.bootcamp.clientms.dao;

import com.everis.bootcamp.clientms.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {

  public Mono<Client> findByName(String nombre);

  public Mono<Client> findByNumDoc(String numDoc);

  public Mono<Boolean> existsByNumDoc(String numDoc);
}
