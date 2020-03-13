package com.everis.bootcamp.clientms.service;

import java.net.URI;
import java.util.Date;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.everis.bootcamp.clientms.common.Response;
import com.everis.bootcamp.clientms.dao.ClientRepository;
import com.everis.bootcamp.clientms.model.Client;

import reactor.core.publisher.Flux;

@Service
public class ClientServiceImpl implements ClientService{

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
	public Mono<Response> save(Client cl) {
		if (cl.getJoinDate() == null) {
			cl.setJoinDate(new Date());
		}else {
			cl.setJoinDate(cl.getJoinDate());
		}

		if(cl.getBusiness() == null && cl.getPersonal() == null) {
			Response respuesta = new Response("0","Es obligatorio llenar los campos business o personal");
			return Mono.justOrEmpty(respuesta);
		}

		if(cl.getBusiness() == cl.getPersonal()) {
			Response respuesta = new Response("0","Los campos business y personal no pueden ser iguales");
			return Mono.justOrEmpty(respuesta);
		}

		if(cl.getBusiness()!=null && cl.getBusiness()==true) {
			cl.setPersonal(false);
		}

		if(cl.getPersonal()!=null && cl.getPersonal()==true) {
			cl.setBusiness(false);
		}


		clientRepo.save(cl).subscribe(clAux -> {
			log.info("Cliente guardado" + clAux.getId() + " " + clAux.getName());
		});


		Response respuesta=new Response("1","Cliente guardado exitosamente",cl);
		return Mono.justOrEmpty(respuesta);

	}

	@Override
	public Mono<Client> create (Client cl) {
		if (cl.getJoinDate() == null) {
			cl.setJoinDate(new Date());
		}else {
			cl.setJoinDate(cl.getJoinDate());
		}

		if(cl.getBusiness() == null && cl.getPersonal() == null) {
			return Mono.empty();
		}

		if(cl.getBusiness() == cl.getPersonal()) {
			return Mono.empty();
		}

		if(cl.getBusiness()!=null && cl.getBusiness()==true) {
			cl.setPersonal(false);
		}

		if(cl.getPersonal()!=null && cl.getPersonal()==true) {
			cl.setBusiness(false);
		}

		return clientRepo.save(cl);
	}


	@Override
	public Mono<Client> update(Client cl, String id) {
		return clientRepo.findById(id)
				.flatMap(dbClient -> {

					//JoinDate
					if(cl.getJoinDate() == null) {
						dbClient.setJoinDate(new Date());
					}else {
						dbClient.setJoinDate(cl.getJoinDate());
					}

					//name
					if(cl.getName() != null) {
						dbClient.setName(cl.getName());
					}

					//NumDoc
					if(cl.getNumDoc() != null) {
						dbClient.setNumDoc(cl.getNumDoc());
					}

					//Address
					if(cl.getAddress() != null) {
						dbClient.setAddress(cl.getAddress());
					}

					//Age
					if(cl.getAge() != 0) {
						dbClient.setAge(cl.getAge());
					}

					//cellphone
					if(cl.getCellphone() != null) {
						dbClient.setCellphone(cl.getCellphone());
					}

					return clientRepo.save(dbClient);

				});

	}

	@Override
	public Mono<Response> updateV2(Client cl, String id) {	

		Response respuesta = new Response();

		clientRepo.findById(id)
		.flatMap(dbClient -> {
			//JoinDate
			if(cl.getJoinDate() == null) {
				dbClient.setJoinDate(new Date());
			}else {
				dbClient.setJoinDate(cl.getJoinDate());
			}

			//name
			if(cl.getName() != null) {
				dbClient.setName(cl.getName());
			}

			//NumDoc
			if(cl.getNumDoc() != null) {
				dbClient.setNumDoc(cl.getNumDoc());
			}

			//Address
			if(cl.getAddress() != null) {
				dbClient.setAddress(cl.getAddress());
			}

			//Age
			if(cl.getAge() != 0) {
				dbClient.setAge(cl.getAge());
			}

			//cellphone
			if(cl.getCellphone() != null) {
				dbClient.setCellphone(cl.getCellphone());
			}

			//personal
			if(cl.getPersonal() != null) {
				dbClient.setPersonal(cl.getPersonal());
			}

			//business
			if(cl.getBusiness() != null) {
				dbClient.setBusiness(cl.getBusiness());
			}	

			//validaciones
			if(dbClient.getBusiness() == dbClient.getPersonal()) {
				respuesta.setCode("0");
				respuesta.setMessage("Los campos business y personal no pueden ser iguales");

			}else {
				clientRepo.save(dbClient).subscribe();
				respuesta.setCode("0");
				respuesta.setMessage("Cliente actualizado exitosamente");
			}
			return Mono.justOrEmpty(respuesta);
		});


		return Mono.empty();
	}

	@Override
	public Mono<Void> delete(Client c) {
		return clientRepo.delete(c);
	}

}
