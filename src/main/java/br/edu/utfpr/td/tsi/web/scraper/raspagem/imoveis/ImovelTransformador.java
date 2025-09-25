package br.edu.utfpr.td.tsi.web.scraper.raspagem.imoveis;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import br.edu.utfpr.td.tsi.web.scraper.etl.Transformador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Imovel;

public class ImovelTransformador extends Transformador<Imovel, Imovel>{

    @Override
    public Imovel transformar(Imovel imovel) {
        if (imovel == null) {
            return null;
        }

        if (imovel.getTitulo() != null && imovel.getTitulo().trim().isEmpty()) {
            imovel.setTitulo(null);
        }
        if (imovel.getFinalidade() != null && imovel.getFinalidade().trim().isEmpty()) {
            imovel.setFinalidade(null);
        }
        if (imovel.getBairro() != null && imovel.getBairro().trim().isEmpty()) {
            imovel.setBairro(null);
        }
        if (imovel.getCidade() != null && imovel.getCidade().trim().isEmpty()) {
            imovel.setCidade(null);
        }
        if (Objects.equals(imovel.getReferencia(), 0)) {
            imovel.setReferencia(null);
        }
        if (Objects.equals(imovel.getNumeroBanheiros(), 0)) {
            imovel.setNumeroBanheiros(null);
        }
        if (Objects.equals(imovel.getNumeroVagasGaragem(), 0)) {
            imovel.setNumeroVagasGaragem(null);
        }

        imovel.setAreaTerreno(formatarArea(imovel.getAreaTerreno()));
        imovel.setAreaConstruida(formatarArea(imovel.getAreaConstruida()));
        imovel.setAreaUtil(formatarArea(imovel.getAreaUtil()));
        
        imovel.setValor(formatarValor(imovel.getValor()));

        return imovel;
    }

    private static String formatarArea(String area) {
        if (area == null || area.trim().isEmpty()) {
            return null;
        }
        try {
            BigDecimal areaNumerica = new BigDecimal(area.trim().replace(",", "."));
            NumberFormat formatadorNumero = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));
            return formatadorNumero.format(areaNumerica) + " m²";
        } catch (NumberFormatException e) {
            return area;
        }
    }

    private static String formatarValor(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        try {
            BigDecimal valorNumerico = new BigDecimal(valor.trim().replace(",", "."));
            NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
            return formatadorMoeda.format(valorNumerico);
        } catch (NumberFormatException e) {
            return valor;
        }
    }

}
