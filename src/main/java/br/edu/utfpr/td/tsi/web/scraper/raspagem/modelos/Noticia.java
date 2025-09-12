package br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos;

public class Noticia {
	String manchete;
	String linkNoticia;
	
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
}
