package com.everis.bootcamp.clientms.service.impl;

import com.everis.bootcamp.clientms.dao.ClientRepository;
import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.service.ClientService;
import com.everis.bootcamp.clientms.service.ExternalCallService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

  private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

  @Autowired
  private ClientRepository clientRepo;

  @Autowired
  private ExternalCallService externalCall;

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
      Mono<Boolean> existeBanco = externalCall.getExistBank(cl.getBankId());

      return existeBanco.flatMap(existe -> {
        if (existe) {
          return externalCall.verifyProfile(cl.getBankId(), cl.getIdClientType())
              .flatMap(verify -> {
                if (verify) {
                  if (cl.getJoinDate() == null) {
                    cl.setJoinDate(new Date());
                  } else {
                    cl.setJoinDate(cl.getJoinDate());
                  }
                  return clientRepo.save(cl);
                } else {
                  return Mono.error(new Exception("El tipo de cliente no existe en el banco"));
                }
              });
        } else {
          return Mono.error(new Exception("El banco del cliente no existe"));
        }
      });

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
  public Mono<String> delete(String id) {
    try {
      return clientRepo.findById(id).map(cl -> {
        clientRepo.delete(cl).subscribe();
        return cl.getId();
      }).switchIfEmpty(Mono.empty());
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

  @Override
  public Mono<Boolean> existsByNumDoc(String numDoc) {
    return clientRepo.existsByNumDoc(numDoc);
  }

  @Override
  public Mono<String> getClientTypeByNumDoc(String numDoc) {
    try {
      Mono<Client> cliente = clientRepo.findByNumDoc(numDoc)
          .switchIfEmpty(Mono.justOrEmpty(new Client()));
      Mono<String> ret = cliente.map(cl -> {
        logger.info("ENCONTRE ESTO :" + cl.getIdClientType());
        if (cl.getIdClientType() != null) {
          return cl.getIdClientType();
        } else {
          return "-1";
        }
      });
      return ret;
    } catch (Exception e) {
      return Mono.error(e);
    }
  }

}
