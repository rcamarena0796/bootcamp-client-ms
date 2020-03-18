package com.everis.bootcamp.clientms.service;

import com.everis.bootcamp.clientms.model.ClientType;
import reactor.core.publisher.Mono;

public interface ClientTypeService {
    public Mono<ClientType> findByNumId(int numId);
}
