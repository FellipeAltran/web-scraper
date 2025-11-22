package br.edu.utfpr.td.tsi.web.scraper.persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

@Component
public class GravadorArquivoJson {

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public <T> void gravarArquivo(List<T> dadosRaspados, String nomeArquivo) {
		try {
			FileWriter writer = new FileWriter(nomeArquivo);
			gson.toJson(dadosRaspados, writer);
			writer.close();
		} catch (JsonIOException | IOException e) {
		}		
	}

}
