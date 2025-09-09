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
    private WebDriver driver = new ChromeDriver();
    private boolean driverEstaAberto = false;

    /*
     * Vai navegar para a página que passar na url
     * tempoAguardar serve para determinar o tempo que deve aguardar após a página abrir
     * se o webdriver ainda não abriu uma janela do navegador, abre uma nova janela, após isso vai navegar utilizando a mesma janela
     */
    public Document recuperarPagina(String url, Duration tempoAguardar) {
        if (!driverEstaAberto) {
            driverEstaAberto = true;
            driver.get(url);
        } else {
            driver.navigate().to(url);
        }
        
        if (tempoAguardar != null) {
            try {
                Thread.sleep(tempoAguardar);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
		String html = driver.getPageSource();
		return Jsoup.parse(html);
    }
    
    /*
     * Finaliza o webdriver (fecha a janela do navegador)
     */
    public void fecharDriver() {
    	driver.quit();
    }
}