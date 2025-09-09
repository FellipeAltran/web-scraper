package br.edu.utfpr.td.tsi.web.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.utfpr.td.tsi.web.scraper.persistencia.GravadorArquivoJson;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Main {

	@Autowired
	private GravadorArquivoJson gravadorArquivoJson;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

	}

	@PostConstruct
	public void raspagemJsoup() {
		
	}
}