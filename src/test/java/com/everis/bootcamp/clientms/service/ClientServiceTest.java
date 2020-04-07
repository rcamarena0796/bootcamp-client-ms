package com.everis.bootcamp.clientms.service;

import static org.mockito.Mockito.when;

import com.everis.bootcamp.clientms.dao.ClientRepository;
import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(SpringExtension.class)
class ClientServiceTest {

  @TestConfiguration
  static class ClientServiceTestContextConfiguration {

    @Bean
    public ClientService clientService() {
      return new ClientServiceImpl();
    }
  }

  @Autowired
  private ClientService clientService;

  @MockBean
  private ClientRepository clientRepository;

  @MockBean
  private ExternalCallService externalCall;

  @Mock
  private Client expectedClient1;

  @Mock
  private Client expectedClient2;

  @Mock
  private Client expectedClient3;


  @BeforeEach
  void setUp() {

    expectedClient1 = Client.builder().id("1").name("Ruben").age(23)
        .bankId("1").numDoc("132546").cellphone("987654321").address("aaaa").idClientType("1")
        .build();

    expectedClient2 = Client.builder().id("2").name("Ruben").age(23)
        .bankId("1").numDoc("132546").cellphone("987654321").address("aaaa").idClientType("1")
        .build();

    expectedClient3 = Client.builder().id("3").name("Ruben").age(23)
        .bankId("1").numDoc("132546").cellphone("987654321").address("aaaa").idClientType("1")
        .build();

  }

  @Test
  public void whenValidId_thenClientShouldBeFound() {
    when(clientRepository.findById(expectedClient1.getId()))
        .thenReturn(Mono.just(expectedClient1));

    Mono<Client> found = clientService.findById(expectedClient1.getId());

    assertResults(found, expectedClient1);
  }

  @Test
  public void findAll() {
    when(clientRepository.findAll())
        .thenReturn(Flux.just(expectedClient1, expectedClient2, expectedClient3));

    Flux<Client> found = clientService.findAll();

    assertResults(found, expectedClient1, expectedClient2, expectedClient3);
  }

  @Test
  public void whenValidNumDoc_thenClientShouldBeFound() {
    when(clientRepository.findByNumDoc(expectedClient1.getNumDoc()))
        .thenReturn(Mono.just(expectedClient1));

    Mono<Client> found = clientService.findByNumDoc(expectedClient1.getNumDoc());

    assertResults(found, expectedClient1);
  }


  @Test
  public void whenValidNumDoc_thenExistsByNumdocShouldBeTrue() {
    when(clientRepository.existsByNumDoc(expectedClient1.getNumDoc()))
        .thenReturn(Mono.just(true));

    Mono<Boolean> exist = clientService.existsByNumDoc(expectedClient1.getNumDoc());

    StepVerifier
        .create(exist)
        .expectNext(true)
        .verifyComplete();
  }


  @Test
  public void whenValidNumDoc_thenReurnClientType() {
    when(clientRepository.findByNumDoc(expectedClient1.getNumDoc()))
        .thenReturn(Mono.just(expectedClient1));

    Mono<String> clientType = clientService.getClientTypeByNumDoc(expectedClient1.getNumDoc());

    StepVerifier
        .create(clientType)
        .expectNext(expectedClient1.getIdClientType())
        .verifyComplete();
  }

  @Test
  public void save() {
    when(externalCall.verifyProfile(expectedClient1.getBankId(), expectedClient1.getIdClientType()))
        .thenReturn(Mono.just(true));
    when(externalCall.getExistBank(expectedClient1.getBankId())).thenReturn(Mono.just(true));
    when(clientRepository.save(expectedClient1))
        .thenReturn(Mono.just(expectedClient1));

    Mono<Client> saved = clientService.save(expectedClient1);

    assertResults(saved, expectedClient1);
  }

  @Test
  public void update() {
    when(externalCall.verifyProfile(expectedClient1.getBankId(), expectedClient1.getIdClientType()))
        .thenReturn(Mono.just(true));
    when(externalCall.getExistBank(expectedClient1.getBankId())).thenReturn(Mono.just(true));
    when(clientRepository.save(expectedClient1))
        .thenReturn(Mono.just(expectedClient1));
    when(clientRepository.findById(expectedClient1.getId())).thenReturn(Mono.just(expectedClient1));

    Mono<Client> updated = clientService.update(expectedClient1, expectedClient1.getId());
    assertResults(updated, expectedClient1);
  }


  @Test
  public void delete() {
    when(clientRepository.findById(expectedClient1.getId()))
        .thenReturn(Mono.just(expectedClient1));
    when(clientRepository.delete(expectedClient1))
        .thenReturn(Mono.empty());

    Mono<String> deleted = clientService.delete(expectedClient1.getId());

    StepVerifier
        .create(deleted)
        .expectNext(expectedClient1.getId())
        .verifyComplete();
  }

  private void assertResults(Publisher<Client> publisher, Client... expectedCustomers) {
    StepVerifier
        .create(publisher)
        .expectNext(expectedCustomers)
        .verifyComplete();
  }

}