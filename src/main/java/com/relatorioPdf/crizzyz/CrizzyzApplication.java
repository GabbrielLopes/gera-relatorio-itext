package com.relatorioPdf.crizzyz;

import com.relatorioPdf.crizzyz.service.RelatorioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrizzyzApplication implements CommandLineRunner {

	private RelatorioService service;

	public CrizzyzApplication(RelatorioService service) {
		this.service = service;
	}


	public static void main(String[] args) {
		SpringApplication.run(CrizzyzApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.gerarRelatorio();
	}

}
