package br.edu.utfpr.td.tsi.web.scraper.raspagem.imoveis;

import br.edu.utfpr.td.tsi.web.scraper.etl.Transformador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Imovel;

public class ImovelTransformador extends Transformador<Imovel, Imovel>{

	@Override
	public Imovel transformar(Imovel item) {
		
        return item;
	}

}
