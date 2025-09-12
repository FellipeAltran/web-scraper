package br.edu.utfpr.td.tsi.web.scraper.raspagem.g1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
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
			Elements tituloNoticia = listaDeNoticias.get(i).select(".feed-post-body-title");
			String manchete = tituloNoticia.text();
			String linkNoticia = tituloNoticia.attr("href");

			noticia.setLinkNoticia(linkNoticia);
			noticia.setManchete(manchete);

			System.out.print(manchete + linkNoticia + "\n");
			noticias.add(noticia);
		}

		fecharWebDriver();

		this.logger.info("Finalizado raspagem noticias G1");
		
		return noticias;
	}
}
