package br.edu.utfpr.td.tsi.web.scraper.raspagem.restaurantes;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.web.scraper.etl.CarregadorArquivosJson;
import br.edu.utfpr.td.tsi.web.scraper.etl.ExtratorListaItemsArquivosJson;
import br.edu.utfpr.td.tsi.web.scraper.etl.Job;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.AbstractRaspador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Restaurante;
import jakarta.annotation.PostConstruct;

@Component
public class RaspadorMichelinRestaurantes extends AbstractRaspador<Restaurante> {

	private static final String BASE = "https://guide.michelin.com";

	@Value("${file.outputRestaurantes}")
	private String outputRestaurantes;

	@Value("${file.inputRestaurantes}")
	private String inputRestaurantes;

	@Override
	@PostConstruct
	public void iniciarRaspador() {
		List<Restaurante> restaurantes = new RaspadorMichelinRestaurantes().raspar();
		gravadorArquivoJson.gravarArquivo(restaurantes, inputRestaurantes);

		ExtratorListaItemsArquivosJson<Restaurante> extrator = new ExtratorListaItemsArquivosJson<>();
		extrator.setListType(Restaurante.class);
		extrator.setInput(inputRestaurantes);

		RestauranteTransformador transformador = new RestauranteTransformador();
		CarregadorArquivosJson<Restaurante> carregador = new CarregadorArquivosJson<>();
		carregador.setOutput(outputRestaurantes);

		Job<Restaurante, Restaurante> job = new Job<Restaurante, Restaurante>();
		job.setExtrator(extrator);
		job.setTransformador(transformador);
		job.setCarregador(carregador);
		job.executar();
	}

	@Override
	public List<Restaurante> raspar() {
		this.logger.info("Iniciando raspagem Guia MICHELIN (filtrado por estrelas)");

		// URL que você passou (restaurantes do Brasil com 1 ou 2 estrelas)
		String listUrl = BASE + "/br/pt_BR/selection/brazil/restaurants/1-star-michelin/2-stars-michelin";

		// espera um pouco mais na listagem para o JS carregar
		Document listDoc = irPara(listUrl, Duration.ofSeconds(8));

		// 1) Coletar links únicos para páginas de restaurante (preservando ordem)
		Set<String> links = new LinkedHashSet<>();
		Elements as = listDoc.select("a[href*='/restaurant/']");
		for (Element a : as) {
			String href = a.attr("href").trim();
			if (href.isEmpty())
				continue;
			String url = href.startsWith("http") ? href : BASE + (href.startsWith("/") ? href : "/" + href);
			links.add(url);
		}
		this.logger.info("Links de restaurantes encontrados: {}", links.size());

		// Limite opcional para não fazer muitas requisições (ajuste conforme quiser)
		int maxRestaurants = 25; // altere para limitar; coloque Integer.MAX_VALUE para sem limite

		// 2) Visitar cada página e extrair nome, endereço e classificação (1/2
		// estrelas)
		List<Restaurante> lista = new ArrayList<>();
		int count = 0;
		for (String url : links) {
			if (count >= maxRestaurants)
				break;
			count++;

			try {
				Document doc = irPara(url, Duration.ofSeconds(5));

				// nome
				Element h1 = doc.selectFirst("h1.data-sheet__title");
				String nome = h1 != null ? h1.text().trim() : null;

				// endereço (o primeiro bloco de texto na ficha)
				Element addr = doc.selectFirst("div.data-sheet__block--text");
				String endereco = addr != null ? addr.text().replaceAll("\\s+", " ").trim() : null;

				// classificação (procura o texto que indica "Duas Estrelas" ou "Uma Estrela")
				Integer estrelas = null;
				Elements cls = doc.select("div.data-sheet__classification-item--content");
				for (Element c : cls) {
					String txt = c.text().toLowerCase();
					if (txt.contains("duas") || txt.contains("2") || txt.contains("duas estrelas")) {
						estrelas = 2;
						break;
					} else if (txt.contains("uma") || txt.contains("1") || txt.contains("uma estrela")) {
						estrelas = 1;
						break;
					}
				}

				if (nome != null && !nome.isEmpty()) {
					Restaurante r = new Restaurante();
					r.setNome(nome);
					r.setEndereco(endereco);
					r.setEstrelas(estrelas);
					lista.add(r);
				}
			} catch (Exception e) {
				this.logger.warn("Falha ao processar {}: {}", url, e.getMessage());
			}
		}

		fecharWebDriver();

		this.logger.info("Finalizado MICHELIN. Restaurantes coletados: {}", lista.size());
		return lista;
	}
}
