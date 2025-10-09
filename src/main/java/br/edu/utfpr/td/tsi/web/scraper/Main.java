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
import br.edu.utfpr.td.tsi.web.scraper.raspagem.restaurantes.RestauranteTransformador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.restaurantes.RaspadorMichelinRestaurantes;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Imovel;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Noticia;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Restaurante;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Main {

	@Autowired
	private GravadorArquivoJson gravadorArquivoJson;

	@Value("${file.outputNoticias}")
	private String outputNoticias;

	@Value("${file.inputNoticias}")
	private String inputNoticias;

	@Value("${file.outputImoveis}")
	private String outputImoveis;

	@Value("${file.inputImoveis}")
	private String inputImoveis;

	@Value("${file.outputRestaurantes}")
	private String outputRestaurantes;

	@Value("${file.inputRestaurantes}")
	private String inputRestaurantes;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@PostConstruct
	public void raspagemJsoup() {
		Thread t1 = new Thread(() -> {
			configNoticias();
		});
		Thread t2 = new Thread(() -> {
			configImoveis();
		});
		Thread t3 = new Thread(() -> {
			configRestaurantes();
		});

		t1.start();
		t2.start();
		t3.start();
	}

	public void configNoticias() {
		List<Noticia> noticias = new RaspadorG1Noticias().raspar();
		gravadorArquivoJson.gravarArquivo(noticias, inputNoticias);

		ExtratorListaItemsArquivosJson<Noticia> extrator = new ExtratorListaItemsArquivosJson<Noticia>();
		extrator.setListType(Noticia.class);
		extrator.setInput(inputNoticias);

		Transformador<Noticia, Noticia> transformador = new NoticiasTransformador();
		CarregadorArquivosJson<Noticia> carregador = new CarregadorArquivosJson<Noticia>();
		carregador.setOutput(outputNoticias);

		Job<Noticia, Noticia> job = new Job<Noticia, Noticia>(Noticia.class);
		job.setExtrator(extrator);
		job.setTransformador(transformador);
		job.setCarregador(carregador);
		job.executar();
	}

	public void configImoveis() {
		List<Imovel> imoveis = new RaspadorImoveis().raspar();
		gravadorArquivoJson.gravarArquivo(imoveis, inputImoveis);

		ExtratorListaItemsArquivosJson<Imovel> extrator = new ExtratorListaItemsArquivosJson<Imovel>();
		extrator.setListType(Imovel.class);
		extrator.setInput(inputImoveis);

		Transformador<Imovel, Imovel> transformador = new ImovelTransformador();
		CarregadorArquivosJson<Imovel> carregador = new CarregadorArquivosJson<Imovel>();
		carregador.setOutput(outputImoveis);

		Job<Imovel, Imovel> job = new Job<Imovel, Imovel>(Imovel.class);
		job.setExtrator(extrator);
		job.setTransformador(transformador);
		job.setCarregador(carregador);
		job.executar();
	}

	public void configRestaurantes() {
		List<Restaurante> restaurantes = new RaspadorMichelinRestaurantes().raspar();
		gravadorArquivoJson.gravarArquivo(restaurantes, inputRestaurantes);

		ExtratorListaItemsArquivosJson<Restaurante> extrator = new ExtratorListaItemsArquivosJson<>();
		extrator.setListType(Restaurante.class);
		extrator.setInput(inputRestaurantes);

		RestauranteTransformador transformador = new RestauranteTransformador();
		CarregadorArquivosJson<Restaurante> carregador = new CarregadorArquivosJson<>();
		carregador.setOutput(outputRestaurantes);

		Job<Restaurante, Restaurante> job = new Job<Restaurante, Restaurante>(Restaurante.class);
		job.setExtrator(extrator);
		job.setTransformador(transformador);
		job.setCarregador(carregador);
		job.executar();
	}
}