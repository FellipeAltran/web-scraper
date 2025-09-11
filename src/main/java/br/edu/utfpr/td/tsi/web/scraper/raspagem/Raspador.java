package br.edu.utfpr.td.tsi.web.scraper.raspagem;

import java.time.Duration;
import java.util.List;
import org.jsoup.nodes.Document;

public interface Raspador<T> {
	public List<T> raspar();
	public Document irPara(String url, Duration tempoEspera);
	public void fecharWebDriver();
}