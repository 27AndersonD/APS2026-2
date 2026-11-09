package com.unip.aps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApsApplication.class, args);
        System.out.println("==================================================");
        System.out.println("   PAINEL WEB INICIADO COM SUCESSO       ");
        System.out.println("   Link: http://localhost:8080                 ");
        System.out.println("==================================================");
    }
}