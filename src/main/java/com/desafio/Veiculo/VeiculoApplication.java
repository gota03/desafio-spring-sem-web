package com.desafio.Veiculo;

import com.desafio.Veiculo.principal.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VeiculoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VeiculoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu menu = new Menu();
		menu.exibeMenu();
	}
}
