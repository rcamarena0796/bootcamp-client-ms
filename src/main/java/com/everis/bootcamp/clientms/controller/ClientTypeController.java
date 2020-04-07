package com.everis.bootcamp.clientms.controller;

import com.everis.bootcamp.clientms.model.ClientType;
import com.everis.bootcamp.clientms.service.ClientTypeService;
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

@Api(tags = "Client type API", value = "CRUD operations for client type")
@RestController
@RequestMapping("/api/clientType")
public class ClientTypeController {

  @Autowired
  private ClientTypeService service;

  /**
   * CONTROLADOR DE TIPO DE CLIENTE.
   */
  @ApiOperation(value = "Service used to return all client  type")
  @GetMapping("/findAll")
  public Flux<ClientType> findAll() {
    return service.findAll();
  }

  @ApiOperation(value = "Service used to find a client by id")
  @GetMapping("/find/{numId}")
  public Mono<ClientType> findByNumId(@PathVariable("id") String id) {
    return service.findByNumId(id);
  }

  /**
   * GUARDAR UN tipo de CLIENTE.
   */
  @ApiOperation(value = "Service used to create client type")
  @PostMapping("/save")
  public Mono<ResponseEntity<ClientType>> create(@Valid @RequestBody ClientType cl) {
    return service.save(cl)
        .map(c -> ResponseEntity
            .created(URI.create("/clientType".concat(c.getId())))
            .contentType(MediaType.APPLICATION_JSON)
            .body(c));
  }

  /**
   * ACTUALIZAR UN TIPO DE CLIENTE.
   */
  @ApiOperation(value = "Service used to update a client type")
  @PutMapping("/update/{id}")
  public Mono<ResponseEntity<ClientType>> update(@PathVariable("id") String id,
      @RequestBody ClientType cl) {
    return service.update(cl, id)
        .map(c -> ResponseEntity
            .created(URI.create("/clientType".concat(c.getId())))
            .contentType(MediaType.APPLICATION_JSON)
            .body(c))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * ELIMINAR UN TIPO DE CLIENTE.
   */
  @ApiOperation(value = "Service used to delete a client type")
  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    return service.delete(id)
        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
        .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }
}