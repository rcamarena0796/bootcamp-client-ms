package com.everis.bootcamp.clientms.service.impl;

import com.everis.bootcamp.clientms.dao.ClientTypeRepository;
import com.everis.bootcamp.clientms.model.ClientType;
import com.everis.bootcamp.clientms.service.ClientTypeService;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientTypeServiceImpl implements ClientTypeService {

    private static final Logger log = LoggerFactory.getLogger(ClientTypeServiceImpl.class);

    @Autowired
    private ClientTypeRepository repo;


    @Override
    public Mono<ClientType> findByNumId(int numId) {
        return repo.findByNumId(numId);
    }
}