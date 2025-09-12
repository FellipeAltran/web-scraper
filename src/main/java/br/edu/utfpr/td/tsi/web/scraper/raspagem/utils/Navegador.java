package br.edu.utfpr.td.tsi.web.scraper.raspagem.utils;

import java.time.Duration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * Classe Navegador serve para controlar a navegação do webdriver
 */

public class Navegador {
	private WebDriver driver;

	/*
	 * Vai navegar para a página que passar na url tempoAguardar serve para
	 * determinar o tempo que deve aguardar após a página abrir se o webdriver ainda
	 * não abriu uma janela do navegador, abre uma nova janela, após isso vai
	 * navegar utilizando a mesma janela
	 */
	public Document recuperarPagina(String url, Duration tempoAguardar) {
		if (driver == null) {
			driver = new ChromeDriver();
		} 
		
		driver.navigate().to(url);

		if (tempoAguardar != null) {
			driver.manage().timeouts().implicitlyWait(tempoAguardar);
		}

		String html = driver.getPageSource();
		return Jsoup.parse(html);
	}

	/*
	 * Finaliza o webdriver (fecha a janela do navegador)
	 */
	public void fecharDriver() {
		if (driver != null)
			driver.quit();
	}
}