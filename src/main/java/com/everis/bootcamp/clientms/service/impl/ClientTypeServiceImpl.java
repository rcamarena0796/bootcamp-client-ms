package com.everis.bootcamp.clientms.service.impl;

import com.everis.bootcamp.clientms.dao.ClientTypeRepository;
import com.everis.bootcamp.clientms.model.ClientType;
import com.everis.bootcamp.clientms.service.ClientTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ClientTypeServiceImpl implements ClientTypeService {

  private static final Logger log = LoggerFactory.getLogger(ClientTypeServiceImpl.class);

  @Autowired
  private ClientTypeRepository repo;

  @Override
  public Flux<ClientType> findAll() {
    return repo.findAll();
  }

  @Override
  public Mono<ClientType> findByNumId(String numId) {
    return repo.findByNumId(numId);
  }

  @Override
  public Mono<ClientType> save(ClientType cl) {
    try {
      return repo.save(cl);
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<ClientType> update(ClientType clt, String id) {
    try {
      return repo.findById(id)
          .flatMap(dbClientType -> {

            if (clt.getName() != null) {
              dbClientType.setName(clt.getName());
            }

            return repo.save(dbClientType);

          }).switchIfEmpty(Mono.empty());
    } catch (Exception e) {
      return Mono.error(e);
    }

  }

  @Override
  public Mono<Void> delete(String id) {
    try {
      return repo.findById(id).flatMap(cl -> {
        return repo.delete(cl);
      });
    } catch (Exception e) {
      return Mono.error(e);
    }
  }
}