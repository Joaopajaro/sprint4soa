package com.example.soaauthapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação SOA de autenticação. Esta classe contém o
 * método main que inicia o contexto Spring. O objetivo da aplicação é
 * demonstrar conceitos de arquitetura orientada a serviços com
 * autenticação/autorizações seguras usando JWT. O código foi escrito
 * de forma simples e comentada para fins didáticos, como seria
 * desenvolvido por um estudante.
 */
@SpringBootApplication
public class SoaAuthAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoaAuthAppApplication.class, args);
    }
}