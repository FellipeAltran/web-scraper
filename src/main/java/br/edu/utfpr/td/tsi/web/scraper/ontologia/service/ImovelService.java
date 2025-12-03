package br.edu.utfpr.td.tsi.web.scraper.ontologia.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import br.edu.utfpr.td.tsi.web.scraper.persistencia.GerenciadorArquivoJson;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Imovel;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ImovelService {
	@Value("${file.outputImoveis}")
	private String outputImoveis;

	@Autowired
	private GerenciadorArquivoJson gerenciadorArquivoJson;

	private List<Imovel> imoveis;

	private void garantirImoveisGarregados() {
		if (imoveis == null || imoveis.isEmpty()) {
			imoveis = gerenciadorArquivoJson.lerArquivo(outputImoveis, Imovel[].class);
		}
	}

	public List<Imovel> listarTodos() {
		garantirImoveisGarregados();

		return imoveis;
	}

	public Imovel buscarPorId(Integer id) {
		garantirImoveisGarregados();

		return imoveis.stream().filter(n -> n.getId().equals(id)).findFirst().orElse(null);
	}
	
	public List<Map<String, Object>> gerarOntologia(HttpServletRequest request) {
		List<Map<String, Object>> ontologia = new ArrayList<>();
		
		List<Imovel> imoveis = listarTodos();

		String baseUrl = construirBaseUrl(request);

		for (Imovel imovel : imoveis) {
			ontologia.add(construirOntologiaPorImovel(imovel, baseUrl));
		}
		
		return ontologia;
	}
	
	public Map<String, Object> gerarOntologiaPorId(HttpServletRequest request, Integer id) {
		Imovel imovel = buscarPorId(id);
		
		return construirOntologiaPorImovel(imovel, construirBaseUrl(request));
	}
	
	
	private Map<String, Object> construirOntologiaPorImovel(Imovel imovel, String baseUrl) {
	    Map<String, Object> context = new LinkedHashMap<>();

	    context.put("@context", "https://schema.org");
	    context.put("@type", "Offer");
	    
	    context.put("@id", baseUrl + "/imoveis/" + imovel.getId());

	    context.put("priceCurrency", "BRL");
	    context.put("price", limparValorNumerico(imovel.getValor())); 
	    
	    if (imovel.getFinalidade() != null && imovel.getFinalidade().toLowerCase().contains("venda")) {
	        context.put("businessFunction", "http://purl.org/goodrelations/v1#Sell");
	    } else {
	        context.put("businessFunction", "http://purl.org/goodrelations/v1#LeaseOut");
	    }

	    Map<String, Object> house = new LinkedHashMap<>();
	    house.put("@type", "House");
	    house.put("name", imovel.getTitulo());
	    house.put("numberOfBathroomsTotal", imovel.getNumeroBanheiros());
	    
	    house.put("description", "Imóvel localizado em " + imovel.getCidade() + 
	                             " com " + imovel.getNumeroVagasGaragem() + " vagas de garagem.");

	    Map<String, Object> address = new LinkedHashMap<>();
	    address.put("@type", "PostalAddress");
	    address.put("addressLocality", imovel.getCidade());
	    address.put("addressRegion", "PR");
	    address.put("streetAddress", imovel.getBairro());
	    house.put("address", address);

	    String areaTexto = imovel.getAreaUtil();
	    if (areaTexto == null || areaTexto.isEmpty()) areaTexto = imovel.getAreaConstruida();
	    if (areaTexto == null || areaTexto.isEmpty()) areaTexto = imovel.getAreaTerreno();

	    if (areaTexto != null && !areaTexto.isEmpty()) {
	        Map<String, Object> floorSize = new LinkedHashMap<>();
	        floorSize.put("@type", "QuantitativeValue");
	        floorSize.put("value", limparValorNumerico(areaTexto));
	        floorSize.put("unitCode", "SQM");
	        house.put("floorSize", floorSize);
	    }


	    context.put("itemOffered", house);

	    return context;
	}
	
	private String limparValorNumerico(String valorBruto) {
	    if (valorBruto == null) return "0";

	    String limpo = valorBruto.replaceAll("[^0-9,.]", "");
	    return limpo.replace(",", ".");
	}
	
	private String construirBaseUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
	}
}
