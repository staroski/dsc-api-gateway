package br.com.staroski.dsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DscApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DscApiGatewayApplication.class, args);
	}

}
