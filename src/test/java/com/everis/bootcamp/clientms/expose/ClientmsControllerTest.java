package com.everis.bootcamp.clientms.expose;



import com.everis.bootcamp.clientms.model.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest
public class ClientmsControllerTest {

    @Autowired
    private WebTestClient webClient;

    private static Client clientTest;

    @BeforeAll
    public static void setup() {
        clientTest = new Client();
        clientTest.setName("Ruben");
    }

    @Test
    public void test_controller_hola_mundo(){
        webClient.get()
                .uri("/api/client/test")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Client.class)
                .isEqualTo(clientTest);
    }
}
