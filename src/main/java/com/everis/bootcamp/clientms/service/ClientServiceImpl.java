package com.everis.bootcamp.clientms.service;

import java.util.Date;
import java.util.Optional;

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
        if (cl.getJoinDate() == null) {
            cl.setJoinDate(new Date());
        } else {
            cl.setJoinDate(cl.getJoinDate());
        }

        return clientRepo.save(cl);
    }


    @Override
    public Mono<Client> update(Client cl, String id) {
        return clientRepo.findById(id)
                .flatMap(dbClient -> {

                    //JoinDate
                    if (!Optional.of(cl.getJoinDate()).equals(Optional.of(null))) {
                        dbClient.setJoinDate(cl.getJoinDate());
                    }

                    //name
                    if (!Optional.of(cl.getName()).equals(Optional.of(null))) {
                        dbClient.setName(cl.getName());
                    }

                    //NumDoc
                    if (!Optional.of(cl.getNumDoc()).equals(Optional.of(null))) {
                        dbClient.setNumDoc(cl.getNumDoc());
                    }

                    //Address
                    if (!Optional.of(cl.getAddress()).equals(Optional.of(null))) {
                        dbClient.setAddress(cl.getAddress());
                    }

                    //Age
                    if (!Optional.of(cl.getAge()).equals(Optional.of(null))) {
                        dbClient.setAge(cl.getAge());
                    }

                    //cellphone
                    if (!Optional.of(cl.getCellphone()).equals(Optional.of(null))) {
                        dbClient.setCellphone(cl.getCellphone());
                    }

                    return clientRepo.save(dbClient);

                }).switchIfEmpty(Mono.empty());

    }

    @Override
    public Mono<Void> delete(String id) {
        return clientRepo.findById(id).flatMap(cl -> {
            return clientRepo.delete(cl);
        });
    }

}
