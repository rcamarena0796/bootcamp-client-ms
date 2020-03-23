package com.everis.bootcamp.clientms.expose;

import com.everis.bootcamp.clientms.controller.ClientController;
import com.everis.bootcamp.clientms.dao.ClientRepository;
import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.service.ClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClientController.class)
@Import(ClientService.class)
public class ClientmsControllerTest {

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
                .uri("/api/client/test")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Client.class)
                .isEqualTo(clientTest);
    }
}
