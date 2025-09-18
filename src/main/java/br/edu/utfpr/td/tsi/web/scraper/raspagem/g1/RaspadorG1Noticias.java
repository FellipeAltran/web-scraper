package br.edu.utfpr.td.tsi.web.scraper.raspagem.g1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.web.scraper.raspagem.AbstractRaspador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Noticia;

@Component
public class RaspadorG1Noticias extends AbstractRaspador<Noticia> {
   

	@Override
	public List<Noticia> raspar() {
		this.logger.info("Iniciando raspagem noticias G1");
		
		Document document = irPara("https://g1.globo.com", Duration.ofSeconds(3));

		List<Noticia> noticias = new ArrayList<Noticia>();

		Elements listaDeNoticias = document.select(".feed-post-body");

		for (var i = 0; i < listaDeNoticias.size(); i++) {
			Noticia noticia = new Noticia();
			List<Noticia> relacionadas = new ArrayList<Noticia>();
			
			Element n = listaDeNoticias.get(i);
			
			Elements corpoTitulo = n.select(".feed-post-body-title");
			String manchete = corpoTitulo.text();
			String linkNoticia = corpoTitulo.attr("href");
			
			String tempoPublicada = n.select(".feed-post-datetime").first().ownText();
			String categoria = n.select(".feed-post-metadata-section").text();

			Elements listaRelacionadas = listaDeNoticias.get(i).select(".bstn-relatedtext");
			
			for(var j = 0; j < listaRelacionadas.size(); j++) {
				Element noticiaRelacionada = listaRelacionadas.get(j);
				
				Noticia relacionada = new Noticia();
				String titulo = noticiaRelacionada.text();
				String linkRelacionado = noticiaRelacionada.attr("href");
				
				relacionada.setManchete(titulo);
				relacionada.setLinkNoticia(linkRelacionado);
				relacionadas.add(relacionada);
			}
			
			
			noticia.setLinkNoticia(linkNoticia);
			noticia.setManchete(manchete);
			noticia.setTempoPublicada(tempoPublicada);
			noticia.setCategoria(categoria);
			noticia.setRelacionadas(relacionadas);
			
			noticias.add(noticia);
		}

		fecharWebDriver();

		this.logger.info("Finalizado raspagem noticias G1");
		
		return noticias;
	}
}
