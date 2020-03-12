package com.everis.bootcamp.clientms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.service.ClientService;

import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;



import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/cliente")
public class ClientController {
	
	@Autowired
	private ClientService service;

    @GetMapping("/test")
    public Mono<Client> saludo(){
    	Client hola = new Client();
        hola.setName("Ruben");
        return Mono.justOrEmpty(hola);
    }
    
    @GetMapping("/selectAll")
	public Mono<ResponseEntity<Flux<Client>>> list(){
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
	}
}