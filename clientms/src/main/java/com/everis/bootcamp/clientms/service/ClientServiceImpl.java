package com.everis.bootcamp.clientms.service;


import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.bootcamp.clientms.dao.ClientRepository;
import com.everis.bootcamp.clientms.model.Client;

import reactor.core.publisher.Flux;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientRepository clientRepo;
	
	@Override
	public Mono<Client> findByName(String name) {
		return clientRepo.findByName(name);
	}

	@Override
	public Flux<Client> findAll() {
		return clientRepo.findAll();
	}

}
