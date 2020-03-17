package com.everis.bootcamp.clientms.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.service.ClientService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@Api(tags = "Client API",  description = "CRUD operations for clients")
@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService service;


    @GetMapping("/test")
    public Mono<Client> saludo() {
        Client hola = new Client();
        hola.setName("Ruben");
        return Mono.justOrEmpty(hola);
    }

    @GetMapping("/selectAll")
    public Mono<ResponseEntity<Flux<Client>>> list() {
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType
                        .APPLICATION_JSON)
                .body(service
                        .findAll()));
    }

    @GetMapping("/findAll")
    public Flux<Client> findAll() {
        return service.findAll();
    }

    @GetMapping("/findById/{id}")
    public Mono<Client> findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @GetMapping("/find/{numDoc}")
    public Mono<Client> findByNumDoc(@PathVariable("numDoc") String numDoc) {
        return service.findByNumDoc(numDoc);
    }

    //GUARDAR UN CLIENTE
    @ApiOperation(value = "Servicio utilizado para crear un cliente")
    @PostMapping("/save")
    public Mono<ResponseEntity<Client>> create(@Valid @RequestBody Client cl) {
        return service.save(cl)
                .map(c -> ResponseEntity
                        .created(URI
                                .create("/clients"
                                        .concat(c
                                                .getId())))
                        .contentType(MediaType
                                .APPLICATION_JSON)
                        .body(c));
    }

    //ACTUALIZAR UN CLIENTE
    @ApiOperation(value = "Servicio utilizado para actualizar un cliente")
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Client>> update(@PathVariable("id") String id, @RequestBody Client cl) {
        return service.update(cl, id)
                .map(c -> ResponseEntity
                        .created(URI
                                .create("/clients"
                                        .concat(c
                                                .getId())))
                        .contentType(MediaType
                                .APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity
                        .notFound()
                        .build());
    }

    //ELIMINAR UN CLIENTE
    @ApiOperation(value = "Servicio utilizado para borrar un cliente")
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.delete(id)
                .then(Mono
                        .just(new ResponseEntity<Void>(HttpStatus
                                .NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus
                        .NOT_FOUND));
    }
}