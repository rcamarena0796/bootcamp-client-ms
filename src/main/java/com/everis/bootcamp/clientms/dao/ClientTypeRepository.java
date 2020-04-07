package com.everis.bootcamp.clientms.dao;

import com.everis.bootcamp.clientms.model.ClientType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientTypeRepository extends ReactiveMongoRepository<ClientType, String> {

  public Mono<ClientType> findByNumId(String numId);
}
