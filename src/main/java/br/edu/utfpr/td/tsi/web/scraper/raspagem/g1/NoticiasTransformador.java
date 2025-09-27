package br.edu.utfpr.td.tsi.web.scraper.raspagem.g1;

import br.edu.utfpr.td.tsi.web.scraper.etl.Transformador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Noticia;

public class NoticiasTransformador extends Transformador<Noticia, Noticia> {

	@Override
	public Noticia transformar(Noticia noticia) {
		if (noticia == null) {
			return null;
		}

		if (noticia.getManchete() != null && noticia.getManchete().trim().isEmpty()) {
			noticia.setManchete(null);
		}

		if (noticia.getCategoria() != null && noticia.getCategoria().trim().isEmpty()) {
			noticia.setCategoria(null);
		}

		if (noticia.getLinkNoticia() != null && noticia.getLinkNoticia().trim().isEmpty()) {
			noticia.setLinkNoticia(null);
		}

		if (noticia.getTempoPublicada() != null && noticia.getTempoPublicada().trim().isEmpty()) {
			noticia.setTempoPublicada(null);
		}

		if (noticia.getRelacionadas() != null && noticia.getRelacionadas().isEmpty()) {
			noticia.setRelacionadas(null);
		}

		if (noticia.getRelacionadas() != null) {
			noticia.getRelacionadas().forEach((n) -> {
				if (n.getManchete() != null && n.getManchete().trim().isEmpty()) {
					n.setManchete(null);
				}

				if (n.getLinkNoticia() != null && n.getLinkNoticia().trim().isEmpty()) {
					n.setLinkNoticia(null);
				}
				
				n.setManchete(formatarTexto(n.getManchete()));
			});
		}

		noticia.setManchete(formatarTexto(noticia.getManchete()));

		return noticia;
	}

	private String formatarTexto(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
			return null;
		}

		return texto.replaceAll("\u0027", "");
	}

}
