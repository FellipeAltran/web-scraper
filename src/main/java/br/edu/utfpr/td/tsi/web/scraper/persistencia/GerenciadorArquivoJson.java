package br.edu.utfpr.td.tsi.web.scraper.persistencia;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

@Component
public class GerenciadorArquivoJson {

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public <T> void gravarArquivo(List<T> dadosRaspados, String nomeArquivo) {
		try {
			FileWriter writer = new FileWriter(nomeArquivo);
			gson.toJson(dadosRaspados, writer);
			writer.close();
		} catch (JsonIOException | IOException e) {
		}		
	}
	
	public <T> List<T> lerArquivo(String nomeArquivo, Class<T[]> clazz) {
	    try {
	        FileReader reader = new FileReader(nomeArquivo);

	        T[] array = gson.fromJson(reader, clazz);

	        reader.close();

	        return Arrays.asList(array);
	    } catch (IOException e) {
	        return Collections.emptyList();
	    }
	}

}
