package br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Noticia {
	Integer id;
	String manchete;
	String linkNoticia;
	String tempoPublicada;
	String categoria;
	List<NoticiaRelacionada> relacionadas;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
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

	public List<NoticiaRelacionada> getRelacionadas() {
	    return relacionadas != null ? relacionadas : Collections.emptyList();
	}

	public void setRelacionadas(List<NoticiaRelacionada> relacionadas) {
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
