package com.everis.bootcamp.clientms.controller;

import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(tags = "Client API", value = "CRUD operations for clients")
@RestController
@RequestMapping("/client")
public class ClientController {

  @Autowired
  private ClientService service;

  /**
   * Controlador de clientms.
   */
  @GetMapping("/test")
  public Mono<Client> saludo() {
    Client hola = new Client();
    hola.setName("Ruben");
    return Mono.justOrEmpty(hola);
  }

  @ApiOperation(value = "Service used to return all clients")
  @GetMapping("/findAll")
  public Flux<Client> findAll() {
    return service.findAll();
  }

  @ApiOperation(value = "Service used to find a client by id")
  @GetMapping("/findById/{id}")
  public Mono<Client> findById(@PathVariable("id") String id) {
    return service.findById(id);
  }

  @ApiOperation(value = "Service used to find a client by numDoc")
  @GetMapping("/find/{numDoc}")
  public Mono<Client> findByNumDoc(@PathVariable("numDoc") String numDoc) {
    return service.findByNumDoc(numDoc);
  }

  @ApiOperation(value = "Service used to get the client type by a client numDoc")
  @GetMapping("/getClientType/{numDoc}")
  public Mono<String> getClTypeByNumDoc(@PathVariable("numDoc") String numDoc) {
    return service.getClientTypeByNumDoc(numDoc);
  }

  @ApiOperation(value = "Service used to find if a client exist by numDoc")
  @GetMapping("/exist/{numDoc}")
  public Mono<Boolean> exist(@PathVariable("numDoc") String numDoc) {
    return service.existsByNumDoc(numDoc);
  }

  /**
   * GUARDAR UN CLIENTE.
   */
  @ApiOperation(value = "Service used to create clients")
  @PostMapping("/save")
  public Mono<ResponseEntity<Client>> create(@Valid @RequestBody Client cl) {
    return service.save(cl)
        .map(c -> ResponseEntity
            .created(URI.create("/clients".concat(c.getId())))
            .contentType(MediaType.APPLICATION_JSON)
            .body(c));
  }

  /**
   * ACTUALIZAR UN CLIENTE.
   */
  @ApiOperation(value = "Service used to update a client")
  @PutMapping("/update/{id}")
  public Mono<ResponseEntity<Client>> update(@PathVariable("id") String id,
      @RequestBody Client cl) {
    return service.update(cl, id)
        .map(c -> ResponseEntity
            .created(URI.create("/clients".concat(c.getId())))
            .contentType(MediaType.APPLICATION_JSON)
            .body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * ELIMINAR UN CLIENTE.
   */
  @ApiOperation(value = "Service used to delete a client")
  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    return service.delete(id)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }
}