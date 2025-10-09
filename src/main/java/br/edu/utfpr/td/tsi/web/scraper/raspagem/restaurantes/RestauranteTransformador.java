package br.edu.utfpr.td.tsi.web.scraper.raspagem.restaurantes;

import br.edu.utfpr.td.tsi.web.scraper.etl.Transformador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Restaurante;

public class RestauranteTransformador extends Transformador<Restaurante, Restaurante> {

	@Override
	public Restaurante transformar(Restaurante r) {
        if (r == null) return null;

        if (r.getNome() != null) {
            String nome = r.getNome().trim();
            r.setNome(nome.isEmpty() ? null : nome);
        }
        if (r.getEndereco() != null) {
            String end = r.getEndereco().replaceAll("\\s+", " ").trim();
            r.setEndereco(end.isEmpty() ? null : end);
        }
        if (r.getEstrelas() != null) {
            if (r.getEstrelas() <= 0) r.setEstrelas(null);
            else if (r.getEstrelas() > 2) r.setEstrelas(null); // vira null se algo estranho
        }

        return r;
    }
}
