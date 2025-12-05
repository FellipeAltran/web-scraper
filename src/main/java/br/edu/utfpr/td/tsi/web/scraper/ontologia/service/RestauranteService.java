package br.edu.utfpr.td.tsi.web.scraper.ontologia.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.web.scraper.persistencia.GerenciadorArquivoJson;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Restaurante;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RestauranteService {
	@Value("${file.outputRestaurantes}")
    private String outputRestaurantes;

    @Autowired
    private GerenciadorArquivoJson gerenciadorArquivoJson;

    private List<Restaurante> restaurantes;

    private void garantirRestaurantesCarregados() {
        if (restaurantes == null || restaurantes.isEmpty()) {
            restaurantes = gerenciadorArquivoJson.lerArquivo(outputRestaurantes, Restaurante[].class);
        }
    }

    public List<Restaurante> listarTodos() {
        garantirRestaurantesCarregados();
        return restaurantes;
    }

    public Restaurante buscarPorId(Integer id) {
        garantirRestaurantesCarregados();
        return restaurantes.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // --------- ONTOLOGIA ---------

    public List<Map<String, Object>> gerarOntologia(HttpServletRequest request) {
        List<Map<String, Object>> ontologia = new ArrayList<>();
        List<Restaurante> lista = listarTodos();

        String baseUrl = construirBaseUrl(request);

        for (Restaurante r : lista) {
            ontologia.add(construirOntologiaPorRestaurante(r, baseUrl));
        }

        return ontologia;
    }

    public Map<String, Object> gerarOntologiaPorId(HttpServletRequest request, Integer id) {
        Restaurante restaurante = buscarPorId(id);
        return construirOntologiaPorRestaurante(restaurante, construirBaseUrl(request));
    }

    private Map<String, Object> construirOntologiaPorRestaurante(Restaurante r, String baseUrl) {
        Map<String, Object> context = new LinkedHashMap<>();

        // Basico schema.org
        context.put("@context", "https://schema.org");
        context.put("@type", "Restaurant");
        context.put("@id", baseUrl + "/restaurantes/" + r.getId());

        // Propriedades principais
        context.put("name", r.getNome());
        context.put("url", "https://guide.michelin.com"); 

        // Endereço como PostalAddress 
        if (r.getEndereco() != null && !r.getEndereco().isBlank()) {
            Map<String, Object> address = new LinkedHashMap<>();
            address.put("@type", "PostalAddress");
            address.put("streetAddress", r.getEndereco());
            context.put("address", address);
        }

        // Star Rating (schema.org / Michelin)
        if (r.getEstrelas() != null) {
            Map<String, Object> starRating = new LinkedHashMap<>();
            starRating.put("@type", "Rating");
            starRating.put("ratingValue", r.getEstrelas());
            starRating.put("bestRating", 3); // max de estrelas Michelin
            starRating.put("author", "Guia MICHELIN");
            context.put("starRating", starRating);
        }

        return context;
    }

    private String construirBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    }
}
