package com.everis.bootcamp.clientms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientmsApplication.class, args);
  }

}