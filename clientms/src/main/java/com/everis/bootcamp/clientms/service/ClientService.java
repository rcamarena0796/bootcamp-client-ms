package com.everis.bootcamp.clientms.service;



import com.everis.bootcamp.clientms.common.Response;
import com.everis.bootcamp.clientms.model.Client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
	public Mono<Client> findByName(String name);
	public Mono<Client> findById(String id);
	public Flux<Client> findAll();
	public Mono<Response> saveWithMessage(Client cl);
	public Mono<Client> findByNumDoc(String numDoc);
	public Mono<Client> update(Client c, String id);
	public Mono<Response> updateWithMessage(Client c, String id);
	public Mono<Void> delete(Client c);
	public Mono<Client> save (Client cl);

}
