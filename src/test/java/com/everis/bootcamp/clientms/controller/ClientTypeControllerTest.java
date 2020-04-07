package com.everis.bootcamp.clientms.controller;

import static org.mockito.Mockito.when;

import com.everis.bootcamp.clientms.dao.ClientTypeRepository;
import com.everis.bootcamp.clientms.model.ClientType;
import com.everis.bootcamp.clientms.service.ClientTypeService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClientTypeController.class)
@Import(ClientTypeService.class)
public class ClientTypeControllerTest {

  @Mock
  private List<ClientType> expectedClientTypeList;

  @Mock
  private ClientType expectedClientType;

  @BeforeEach
  void setUp() {

    expectedClientType = ClientType.builder().id("1").numId("1").name("tipo 1").build();

    expectedClientTypeList = Arrays.asList(
        ClientType.builder().id("1").numId("1").name("tipo 1").build(),
        ClientType.builder().id("2").numId("2").name("tipo 2").build(),
        ClientType.builder().id("3").numId("3").name("tipo 3").build()
    );
  }

  @MockBean
  protected ClientTypeService service;

  @MockBean
  ClientTypeRepository repository;

  @Autowired
  private WebTestClient webClient;

  @Test
  void getAllClientType() {
    when(service.findAll()).thenReturn(Flux.fromIterable(expectedClientTypeList));

    webClient.get().uri("/client/clientType/findAll")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(ClientType.class)
        .isEqualTo(expectedClientTypeList);
  }

  @Test
  void getClientTypeByNumId_whenClientTypeExists_returnCorrectClientType() {
    when(service.findByNumId(expectedClientType.getNumId()))
        .thenReturn(Mono.just(expectedClientType));

    webClient.get()
        .uri("/client/clientType/find/{numId}", expectedClientType.getNumId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ClientType.class)
        .isEqualTo(expectedClientType);
  }

  @Test
  void saveClientType() {
    when(service.save(expectedClientType)).thenReturn(Mono.just(expectedClientType));

    webClient.post()
        .uri("/client/clientType/save").body(Mono.just(expectedClientType), ClientType.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ClientType.class)
        .isEqualTo(expectedClientType);
  }

  @Test
  void updateClientType_whenClientTypeExists_performUpdate() {
    when(service.update(expectedClientType, expectedClientType.getId()))
        .thenReturn(Mono.just(expectedClientType));

    webClient.put()
        .uri("/client/clientType/update/{id}", expectedClientType.getId())
        .body(Mono.just(expectedClientType), ClientType.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(ClientType.class)
        .isEqualTo(expectedClientType);
  }

  @Test
  void updateClientType_whenClientTypeNotExist_returnNotFound() {
    String id = "-1";
    when(service.update(expectedClientType, id)).thenReturn(Mono.empty());

    webClient.put()
        .uri("/client/clientType/update/{id}", id)
        .body(Mono.just(expectedClientType), ClientType.class)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void deleteClientType_whenClientTypeExists_performDeletion() {
    when(service.delete(expectedClientType.getId()))
        .thenReturn(Mono.just(expectedClientType.getId()));

    webClient.delete()
        .uri("/client/clientType/delete/{id}", expectedClientType.getId())
        .exchange()
        .expectStatus()
        .isOk();
  }


  @Test
  void deleteClientType_whenClientTypeNotExists_returnNotFound() {
    String id = "-1";
    when(service.delete(id)).thenReturn(Mono.empty());

    webClient.delete()
        .uri("/client/clientType/delete/{id}", id)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

}