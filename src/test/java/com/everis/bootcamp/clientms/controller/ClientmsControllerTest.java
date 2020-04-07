package com.everis.bootcamp.clientms.controller;

import static org.mockito.Mockito.when;

import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.service.ClientService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClientController.class)
@Import(ClientService.class)
public class ClientmsControllerTest {

  @Mock
  private List<Client> expectedClients;

  @Mock
  private Client expectedClient;

  @BeforeEach
  void setUp() {

    expectedClient = Client.builder().id("1").name("Ruben").age(23)
        .bankId("1").numDoc("132546").cellphone("987654321").address("aaaa").idClientType("1").build();

    expectedClients = Arrays.asList(
        Client.builder().id("1").name("Ruben").age(23)
            .bankId("1").numDoc("132546").cellphone("987654321").address("aaaa").idClientType("1").build(),
        Client.builder().id("2").name("Eduardo").age(23)
            .bankId("1").numDoc("132546").cellphone("987654321").address("aaaa").idClientType("1").build(),
        Client.builder().id("3").name("Bruno").age(23)
            .bankId("1").numDoc("132546").cellphone("987654321").address("aaaa").idClientType("1").build()
        );
  }

  @MockBean
  protected ClientService clientService;

  @Autowired
  private WebTestClient webClient;

  private static Client clientTest;

  @BeforeAll
  public static void setup() {
    clientTest = new Client();
    clientTest.setName("Ruben");
  }

  @Test
  public void test_controller_hola_mundo() {
    webClient.get()
        .uri("/client/test")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Client.class)
        .isEqualTo(clientTest);
  }

  @Test
  void getAllClients() {
    when(clientService.findAll()).thenReturn(Flux.fromIterable(expectedClients));

    webClient.get().uri("/client/findAll")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Client.class)
        .isEqualTo(expectedClients);
  }

  @Test
  void getClientById_whenCustomerExists_returnCorrectCustomer() {
    when(clientService.findById(expectedClient.getId())).thenReturn(Mono.just(expectedClient));

    webClient.get()
        .uri("/client/findById/{id}", expectedClient.getId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Client.class)
        .isEqualTo(expectedClient);
  }

  @Test
  void getClientById_whenCustomerNotExist_returnNotFound() {
    String id = "-1";
    when(clientService.findById(id)).thenReturn(Mono.error(new NotFoundException()));

    webClient.get()
        .uri("client/findById/{id}", id)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void getClientByNumDoc_whenClientExists_returnCorrectClient() {
    when(clientService.findByNumDoc(expectedClient.getNumDoc())).thenReturn(Mono.just(expectedClient));

    webClient.get()
        .uri("/client/find/{numDoc}", expectedClient.getNumDoc())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Client.class)
        .isEqualTo(expectedClient);
  }

  @Test
  void getClientByNumDoc_whenClientNotExist_returnNotFound() {
    String numDoc = "-1";
    when(clientService.findByNumDoc(numDoc)).thenReturn(Mono.error(new NotFoundException()));

    webClient.get()
        .uri("client/find/{numDoc}", numDoc)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void getClientTypeByNumDoc_whenClientExists_returnCorrectClient() {
    when(clientService.getClientTypeByNumDoc(expectedClient.getNumDoc())).thenReturn(Mono.just(expectedClient.getIdClientType()));

    webClient.get()
        .uri("/client/getClientType/{numDoc}", expectedClient.getNumDoc())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .isEqualTo(expectedClient.getIdClientType());
  }

  @Test
  void getClientTypeByNumDoc_whenClientNotExist_returnNotFound() {
    String numDoc = "-1";
    when(clientService.getClientTypeByNumDoc(numDoc)).thenReturn(Mono.error(new NotFoundException()));

    webClient.get()
        .uri("client/getClientType/{numDoc}", numDoc)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void getClientExistsByNumDoc_whenClientExists_returnTrue() {
    when(clientService.existsByNumDoc(expectedClient.getNumDoc())).thenReturn(Mono.just(true));

    webClient.get()
        .uri("/client/exist/{numDoc}", expectedClient.getNumDoc())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Boolean.class)
        .isEqualTo(true);
  }

  @Test
  void getClientExistsByNumDoc_whenClientNotExist_returnFalse() {
    String numDoc = "-1";
    when(clientService.existsByNumDoc(numDoc)).thenReturn(Mono.just(false));

    webClient.get()
        .uri("/client/exist/{numDoc}",numDoc)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Boolean.class)
        .isEqualTo(false);
  }

  @Test
  void saveClient() {
    when(clientService.save(expectedClient)).thenReturn(Mono.just(expectedClient));

    webClient.post()
        .uri("/client/save").body(Mono.just(expectedClient), Client.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Client.class)
        .isEqualTo(expectedClient);
  }

  @Test
  void updateClient_whenClientExists_performUpdate() {
    when(clientService.update(expectedClient, expectedClient.getId())).thenReturn(Mono.just(expectedClient));

    webClient.put()
        .uri("/client/update/{id}", expectedClient.getId()).body(Mono.just(expectedClient), Client.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Client.class)
        .isEqualTo(expectedClient);
  }

  @Test
  void updateClient_whenClientNotExist_returnNotFound() {
    String id = "-1";
    when(clientService.update( expectedClient,id)).thenReturn(Mono.empty());

    webClient.put()
        .uri("/client/update/{id}", id).body(Mono.just(expectedClient), Client.class)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void deleteClient_whenClientExists_performDeletion() {
    when(clientService.delete(expectedClient.getId())).thenReturn(Mono.just(expectedClient.getId()));

    webClient.delete()
        .uri("/client/delete/{id}", expectedClient.getId())
        .exchange()
        .expectStatus()
        .isOk();
  }


  @Test
  void deleteClient_whenClientNotExists_returnNotFound() {
    String id = "-1";
    when(clientService.delete(id)).thenReturn(Mono.empty());

    webClient.delete()
        .uri("/client/delete/{id}", id)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

}
