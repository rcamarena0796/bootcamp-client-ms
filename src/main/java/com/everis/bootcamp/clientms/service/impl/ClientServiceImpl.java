package com.everis.bootcamp.clientms.service.impl;

import com.everis.bootcamp.clientms.dao.ClientRepository;
import com.everis.bootcamp.clientms.dto.ClientProfilesDto;
import com.everis.bootcamp.clientms.model.Client;
import com.everis.bootcamp.clientms.service.ClientService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

  private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

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


  private Mono<Boolean> getExistBank(String numId) {
    String url = "http://localhost:8002/bank/exist/" + numId;
    return WebClient.create()
        .get()
        .uri(url)
        .retrieve()
        .bodyToMono(Boolean.class);
  }

  private Mono<Boolean> verifyProfile(String bankId, String profileId) {
    String url = "http://localhost:8002/bank/bankProfiles/" + bankId;
    Mono<ClientProfilesDto> profilesDto = WebClient.create()
        .get()
        .uri(url)
        .retrieve()
        .bodyToMono(ClientProfilesDto.class);

    return profilesDto.map(p -> {
      if (p.getClientProfiles().contains(profileId)) {
        return true;
      } else {
        return false;
      }
    });
  }

  @Override
  public Mono<Client> save(Client cl) {
    try {
      Mono<Boolean> existeBanco = getExistBank(cl.getBankId());

      return existeBanco.flatMap(existe -> {
        if (existe) {
          return verifyProfile(cl.getBankId(), cl.getIdClientType()).flatMap(verify -> {
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
  public Mono<Void> delete(String id) {
    try {
      return clientRepo.findById(id).flatMap(cl -> {
        return clientRepo.delete(cl);
      });
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
