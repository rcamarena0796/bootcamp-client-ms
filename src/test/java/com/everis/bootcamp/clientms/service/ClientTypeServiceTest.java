package com.everis.bootcamp.clientms.service;

import static org.mockito.Mockito.when;

import com.everis.bootcamp.clientms.dao.ClientTypeRepository;
import com.everis.bootcamp.clientms.model.ClientType;
import com.everis.bootcamp.clientms.service.impl.ClientTypeServiceImpl;
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
class ClientTypeServiceTest {

  @TestConfiguration
  static class ClientTypeServiceTestContextConfiguration {

    @Bean
    public ClientTypeService clientTypeService() {
      return new ClientTypeServiceImpl();
    }
  }

  @Autowired
  private ClientTypeService clientTypeService;

  @MockBean
  private ClientTypeRepository clientTypeRepository;

  @Mock
  private ClientType clientType1;

  @Mock
  private ClientType clientType2;

  @Mock
  private ClientType clientType3;


  @BeforeEach
  void setUp() {

    clientType1 = ClientType.builder().id("1").numId("1").name("tipo 1").build();

    clientType2 = ClientType.builder().id("2").numId("2").name("tipo 2").build();

    clientType3 = ClientType.builder().id("3").numId("3").name("tipo 2").build();

  }

  @Test
  public void findAll() {
    when(clientTypeRepository.findAll())
        .thenReturn(Flux.just(clientType1, clientType2, clientType3));

    Flux<ClientType> found = clientTypeService.findAll();

    assertResults(found, clientType1, clientType2, clientType3);
  }

  @Test
  public void whenValidNumId_thenClientTypeShouldBeFound() {
    when(clientTypeRepository.findByNumId(clientType1.getNumId()))
        .thenReturn(Mono.just(clientType1));

    Mono<ClientType> found = clientTypeService.findByNumId(clientType1.getNumId());

    assertResults(found, clientType1);
  }

  @Test
  public void save() {
    when(clientTypeRepository.save(clientType1)).thenReturn(Mono.just(clientType1));

    Mono<ClientType> saved = clientTypeService.save(clientType1);

    assertResults(saved, clientType1);
  }

  @Test
  public void update() {
    when(clientTypeRepository.save(clientType1))
        .thenReturn(Mono.just(clientType1));
    when(clientTypeRepository.findById(clientType1.getId())).thenReturn(Mono.just(clientType1));

    Mono<ClientType> updated = clientTypeService.update(clientType1, clientType1.getId());
    assertResults(updated, clientType1);
  }


  @Test
  public void delete() {
    when(clientTypeRepository.findById(clientType1.getId()))
        .thenReturn(Mono.just(clientType1));
    when(clientTypeRepository.delete(clientType1))
        .thenReturn(Mono.empty());

    Mono<String> deleted = clientTypeService.delete(clientType1.getId());

    StepVerifier
        .create(deleted)
        .expectNext(clientType1.getId())
        .verifyComplete();
  }

  private void assertResults(Publisher<ClientType> publisher, ClientType... expectedClientTypes) {
    StepVerifier
        .create(publisher)
        .expectNext(expectedClientTypes)
        .verifyComplete();
  }

}