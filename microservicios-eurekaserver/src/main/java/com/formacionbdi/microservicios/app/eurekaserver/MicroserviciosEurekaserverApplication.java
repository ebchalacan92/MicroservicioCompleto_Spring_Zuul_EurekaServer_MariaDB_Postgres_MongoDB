package com.formacionbdi.microservicios.app.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MicroserviciosEurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosEurekaserverApplication.class, args);
	}

}
