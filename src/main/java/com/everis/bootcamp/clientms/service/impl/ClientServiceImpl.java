package com.everis.bootcamp.clientms.service.impl;

import java.util.Date;

import com.everis.bootcamp.clientms.service.ClientService;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.bootcamp.clientms.dao.ClientRepository;
import com.everis.bootcamp.clientms.model.Client;

import reactor.core.publisher.Flux;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepo;


    @Override
    public Mono<Client> findByName(String name) {
        return clientRepo.findByName(name);
    }

    @Override
    public Flux<Client> findAll() {
        return clientRepo.findAll();
    }

    @Override
    public Mono<Client> findById(String id) {
        return clientRepo.findById(id);
    }

    @Override
    public Mono<Client> findByNumDoc(String numDoc) {
        return clientRepo.findByNumDoc(numDoc);
    }

    @Override
    public Mono<Client> save(Client cl) {
        try {
            if (cl.getJoinDate() == null) {
                cl.setJoinDate(new Date());
            } else {
                cl.setJoinDate(cl.getJoinDate());
            }
            return clientRepo.save(cl);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Client> update(Client cl, String id) {
        try {
            return clientRepo.findById(id)
                    .flatMap(dbClient -> {

                        //JoinDate
                        if (cl.getJoinDate() != null) {
                            dbClient.setJoinDate(cl.getJoinDate());
                        }

                        //name
                        if (cl.getName() != null) {
                            dbClient.setName(cl.getName());
                        }

                        //NumDoc
                        if (cl.getNumDoc() != null) {
                            dbClient.setNumDoc(cl.getNumDoc());
                        }

                        //Address
                        if (cl.getAddress() != null) {
                            dbClient.setAddress(cl.getAddress());
                        }

                        //Age
                        if (cl.getAge() != 0) {
                            dbClient.setAge(cl.getAge());
                        }

                        //cellphone
                        if (cl.getCellphone() != null) {
                            dbClient.setCellphone(cl.getCellphone());
                        }

                        return clientRepo.save(dbClient);

                    }).switchIfEmpty(Mono.empty());
        } catch (Exception e) {
            return Mono.error(e);
        }

    }

    @Override
    public Mono<Void> delete(String id) {
        try {
            return clientRepo.findById(id).flatMap(cl -> {
                return clientRepo.delete(cl);
            });
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}