package br.edu.utfpr.td.tsi.web.scraper.raspagem;

import java.time.Duration;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.web.scraper.raspagem.utils.Navegador;

@Component
public abstract class AbstractRaspador<T> implements Raspador<T> {
    protected final Navegador navegador = new Navegador();
    
    @Override
	public Document irPara(String url, Duration tempoEspera){
    	return navegador.recuperarPagina(url, tempoEspera);
    }
}