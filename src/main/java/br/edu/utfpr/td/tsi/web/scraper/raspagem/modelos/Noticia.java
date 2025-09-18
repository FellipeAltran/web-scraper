package br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos;

import java.util.List;

public class Noticia {
	String manchete;
	String linkNoticia;
	String tempoPublicada;
	String categoria;
	List<Noticia> relacionadas;
	
	public void setLinkNoticia(String linkNoticia) {
		this.linkNoticia = linkNoticia;
	}
	
	public void setManchete(String manchete) {
		this.manchete = manchete;
	}
	
	public String getLinkNoticia() {
		return linkNoticia;
	}
	
	public String getManchete() {
		return manchete;
	}

	public List<Noticia> getRelacionadas() {
		return relacionadas;
	}

	public void setRelacionadas(List<Noticia> relacionadas) {
		this.relacionadas = relacionadas;
	}

	public String getTempoPublicada() {
		return tempoPublicada;
	}

	public void setTempoPublicada(String tempoPublicada) {
		this.tempoPublicada = tempoPublicada;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
