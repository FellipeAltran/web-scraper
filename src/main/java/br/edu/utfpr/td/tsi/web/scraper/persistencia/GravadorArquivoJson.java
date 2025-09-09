package br.edu.utfpr.td.tsi.web.scraper.persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
 br.edu.utfpr.tsi.web.scraper.RaspadorHtml;


@Component
public class GravadorArquivoJson {

//	private Logger logger = LoggerFactory.getLogger(RaspadorHtml.class);

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public <T> void gravarArquivo(List<T> dadosRaspados, String nomeArquivo) {
//		logger.atLevel(Level.INFO).setMessage("Iniciando conversão dos dados para JSON").log();
		String json = gson.toJson(dadosRaspados);
//		logger.atLevel(Level.INFO).setMessage(json).log();

		try {
			FileWriter writer = new FileWriter(nomeArquivo.trim() + ".json");
			gson.toJson(dadosRaspados, writer);
			writer.close();
		} catch (JsonIOException | IOException e) {
//			logger.atLevel(Level.DEBUG).setCause(e).setMessage(json).log();
		}
		
//		logger.atLevel(Level.INFO).setMessage("Conversão de dados para JSON concluída").log();
	}

}
