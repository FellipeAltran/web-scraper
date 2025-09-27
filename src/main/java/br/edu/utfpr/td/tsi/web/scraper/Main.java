package br.edu.utfpr.td.tsi.web.scraper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.utfpr.td.tsi.web.scraper.etl.CarregadorArquivosJson;
import br.edu.utfpr.td.tsi.web.scraper.etl.ExtratorListaItemsArquivosJson;
import br.edu.utfpr.td.tsi.web.scraper.etl.Job;
import br.edu.utfpr.td.tsi.web.scraper.etl.Transformador;
import br.edu.utfpr.td.tsi.web.scraper.persistencia.GravadorArquivoJson;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.g1.NoticiasTransformador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.g1.RaspadorG1Noticias;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.imoveis.ImovelTransformador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.imoveis.RaspadorImoveis;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Imovel;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Noticia;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Main {

	@Autowired
	private GravadorArquivoJson gravadorArquivoJson;

	@Value("${file.output}")
	private String output;

	@Value("${file.input}")
	private String input;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@PostConstruct
	public void raspagemJsoup() {
		 configImoveis();
		 // configNoticias();
	}
	
	public void configNoticias() {
		List<Noticia> noticias = new RaspadorG1Noticias().raspar();
		gravadorArquivoJson.gravarArquivo(noticias, "noticias");

		ExtratorListaItemsArquivosJson<Noticia> extrator = new ExtratorListaItemsArquivosJson<Noticia>();
		extrator.setListType(Noticia.class);
		extrator.setInput(input);

		Transformador<Noticia, Noticia> transformador = new NoticiasTransformador();
		CarregadorArquivosJson<Noticia> carregador = new CarregadorArquivosJson<Noticia>();
		carregador.setOutput(output);

		Job<Noticia, Noticia> job = new Job<Noticia, Noticia>();
		job.setExtrator(extrator);
		job.setTransformador(transformador);
		job.setCarregador(carregador);
		job.executar();
	}

	public void configImoveis() {
		List<Imovel> imoveis = new RaspadorImoveis().raspar();
		gravadorArquivoJson.gravarArquivo(imoveis, "imoveis");

		ExtratorListaItemsArquivosJson<Imovel> extrator = new ExtratorListaItemsArquivosJson<Imovel>();
		extrator.setListType(Imovel.class);
		extrator.setInput(input);

		Transformador<Imovel, Imovel> transformador = new ImovelTransformador();
		CarregadorArquivosJson<Imovel> carregador = new CarregadorArquivosJson<Imovel>();
		carregador.setOutput(output);

		Job<Imovel, Imovel> job = new Job<Imovel, Imovel>();
		job.setExtrator(extrator);
		job.setTransformador(transformador);
		job.setCarregador(carregador);
		job.executar();
	}
}