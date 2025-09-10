package br.edu.utfpr.td.tsi.web.scraper.raspagem.g1;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.web.scraper.raspagem.AbstractRaspador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Noticias;

@Component
public class G1Noticias extends AbstractRaspador<Noticias> {
	@Override
	public List<Noticias> raspar() {
		irPara("url", Duration.ofSeconds(1));
		
		List<Noticias> noticias = new ArrayList<Noticias>();

		return noticias;
	}
}
