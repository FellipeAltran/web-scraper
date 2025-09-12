package br.edu.utfpr.td.tsi.web.scraper.raspagem;

import java.time.Duration;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.utils.Navegador;

@Component
public abstract class AbstractRaspador<T> implements Raspador<T> {
    private final Navegador navegador = new Navegador();
    protected final Logger logger;

    protected AbstractRaspador() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
    
    @Override
	public Document irPara(String url, Duration tempoEspera){
    	return navegador.recuperarPagina(url, tempoEspera);
    }
    
    @Override
    public void fecharWebDriver() {
    	navegador.fecharDriver();
    }
}