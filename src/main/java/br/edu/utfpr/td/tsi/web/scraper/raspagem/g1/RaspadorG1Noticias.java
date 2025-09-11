package br.edu.utfpr.td.tsi.web.scraper.raspagem.g1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.web.scraper.raspagem.AbstractRaspador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Noticias;

@Component
public class RaspadorG1Noticias extends AbstractRaspador<Noticias> {
	@Override
	public List<Noticias> raspar() {
		Document document = irPara("https://g1.globo.com", Duration.ofSeconds(3));
		
		List<Noticias> noticias = new ArrayList<Noticias>();
		
	    Elements listaDeNoticias =	document.select("feed-post-body");

	    for(var i = 0; i < listaDeNoticias.size(); i++) {
	      String manchete = listaDeNoticias.get(i).getElementsByAttribute("elementtiming").text();
	      System.out.print(manchete);
	    }
	    
	    fecharWebDriver();
	    
		return noticias;
	}
}
