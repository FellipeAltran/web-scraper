package br.edu.utfpr.td.tsi.web.scraper.raspagem.pichau;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.AbstractRaspador;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Imovel;

@Component
public class RaspadorImoveis extends AbstractRaspador<Imovel> {
	@Override
	public List<Imovel> raspar() {
		Document document = irPara(
				"https://www.imobiliariaativa.com.br/pesquisa-de-imoveis/?locacao_venda=L&finalidade=&dormitorio=&garagem=&vmi=&vma=&ordem=4",
				Duration.ofSeconds(10));
		List<Imovel> listaImoveis = new ArrayList<Imovel>();
		String baseUrl = "https://www.imobiliariaativa.com.br/";

		try {
			List<Element> imoveisCards = document.select(".card");

			for (Element imovelCard : imoveisCards) {
				Imovel imovel = new Imovel();
				String[] localizacao = imovelCard.select(".card-bairro-cidade-texto").text().split("-");
				imovel.setBairro(localizacao[0].trim());
				imovel.setCidade(localizacao[1].trim());
				imovel.setValor(imovelCard.select(".card-valores").text().replaceAll("[^\\d, ]", "").trim().split("\\s+")[0]);

				String imovelUrl = baseUrl + imovelCard.select(".carousel .flickity-viewport .flickity-slider a").attr("href");
				Document imovelDoc = irPara(imovelUrl, Duration.ofSeconds(10));

				imovel.setTitulo(imovelDoc.select(".titulo-imovel").text());
				imovel.setFinalidade(imovelDoc.select(".tipo-prop strong").text());

				String referencia = imovelDoc.select(".codigo-imo .fw-bold").text();
				imovel.setReferencia(referencia.isEmpty() ? 0 : Integer.parseInt(referencia));

				String numeroBanheiros = imovelDoc.select(".banh-ico-imo .fw-bold").text();
				imovel.setNumeroBanheiros(numeroBanheiros.isEmpty() ? 0 : Integer.parseInt(numeroBanheiros));

				String numeroGaragens = imovelDoc.select(".gar-ico-imo .fw-bold").text();
				imovel.setNumeroBanheiros(numeroGaragens.isEmpty() ? 0 : Integer.parseInt(numeroGaragens));

				imovel.setAreaTerreno(imovelDoc.select(".a-terr-ico-imo .fw-bold strong").text().split(" ")[0]);

				imovel.setAreaConstruida(imovelDoc.select(".a-const-ico-imo .fw-bold strong").text().split(" ")[0]);

				imovel.setAreaUtil(imovelDoc.select(".a-util-ico-imo .fw-bold strong").text().split(" ")[0]);

				listaImoveis.add(imovel);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fecharWebDriver();
		}

		return listaImoveis;
	}

}
