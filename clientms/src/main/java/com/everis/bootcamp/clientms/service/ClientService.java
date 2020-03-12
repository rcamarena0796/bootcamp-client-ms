package com.everis.bootcamp.clientms.service;



import com.everis.bootcamp.clientms.model.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
	public Mono<Client> findByName(String name);
	public Flux<Client> findAll();

}
