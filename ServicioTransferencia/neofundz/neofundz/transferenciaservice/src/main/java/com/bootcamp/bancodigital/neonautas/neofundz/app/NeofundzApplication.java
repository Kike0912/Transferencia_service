package com.bootcamp.bancodigital.neonautas.neofundz.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages= {"com.bootcamp.bancodigital.neonautas.neofundz"})
@EntityScan("com.bootcamp.bancodigital.neonautas.neofundz.model")
@EnableJpaRepositories("com.bootcamp.bancodigital.neonautas.neofundz.repository")
public class NeofundzApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeofundzApplication.class, args);
	}

}
