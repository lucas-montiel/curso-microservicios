package com.springcloud.msv.usuarios.microservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroserviciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosApplication.class, args);
	}

}
