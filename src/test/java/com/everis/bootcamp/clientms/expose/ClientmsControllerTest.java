package com.everis.bootcamp.clientms.expose;

import com.everis.bootcamp.clientms.controller.ClientController;
import com.everis.bootcamp.clientms.dao.ClientRepository;
import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.model.ClientType;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClientController.class)
@Import(ClientService.class)
public class ClientmsControllerTest {

  private final ClientType typeCustomer1 = ClientType.builder().name("Type of customer 1").build();
  private final ClientType typeCustomer2 = ClientType.builder().name("Type of customer 2").build();

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

  @MockBean
  ClientRepository repository;

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
}
