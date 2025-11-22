package br.edu.utfpr.td.tsi.web.scraper.ontologia.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.web.scraper.persistencia.GerenciadorArquivoJson;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Noticia;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.NoticiaRelacionada;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class NoticiaService {
	@Value("${file.outputNoticias}")
	private String outputNoticias;

	@Autowired
	private GerenciadorArquivoJson gerenciadorArquivoJson;

	private List<Noticia> noticias;

	private void garantirNoticiasGarregadas() {
		if (noticias == null || noticias.isEmpty()) {
			noticias = gerenciadorArquivoJson.lerArquivo(outputNoticias, Noticia[].class);
		}
	}

	public List<Noticia> listarTodas() {
		garantirNoticiasGarregadas();

		return noticias;
	}

	public Noticia buscarPorId(Integer id) {
		garantirNoticiasGarregadas();

		return noticias.stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
	}
	
	public List<Map<String, Object>> gerarOntologia(HttpServletRequest request) {
		List<Map<String, Object>> ontologia = new ArrayList<>();
		
		List<Noticia> noticias = listarTodas();

		String baseUrl = construirBaseUrl(request);

		for (Noticia noticia : noticias) {
			ontologia.add(construirOntologiaPorNoticia(noticia, baseUrl));
		}
		
		return ontologia;
	}
	
	public Map<String, Object> gerarOntologiaPorId(HttpServletRequest request, Integer id) {
		Noticia noticia = buscarPorId(id);
		
		return construirOntologiaPorNoticia(noticia, construirBaseUrl(request));
	}
	
	
	private Map<String, Object> construirOntologiaPorNoticia(Noticia noticia, String baseUrl) {
		Map<String, Object> context = new HashMap<>();

		context.put("@context", "https://schema.org");
		context.put("@type", "NewsArticle");

		context.put("@id", baseUrl + "/noticias/" + noticia.getId());

		context.put("headline", noticia.getManchete());
		context.put("url", noticia.getLinkNoticia());
		context.put("datePublished", noticia.getTempoPublicada());
		context.put("articleSection", noticia.getCategoria());

		List<Map<String, Object>> relacionadas = new ArrayList<>();
		for (NoticiaRelacionada relacionada : noticia.getRelacionadas()) {
			if(relacionada == null) continue;
			
			Map<String, Object> relMap = new LinkedHashMap<>();
			relMap.put("@type", "NewsArticle");
			relMap.put("headline", relacionada.getManchete());
			relMap.put("url", relacionada.getLinkNoticia());
			relacionadas.add(relMap);
		}

		context.put("mentions", relacionadas);

		return context;
	}
	
	private String construirBaseUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
	}
}
