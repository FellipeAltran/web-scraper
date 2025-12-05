package br.edu.utfpr.td.tsi.web.scraper.ontologia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.td.tsi.web.scraper.ontologia.service.RestauranteService;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Restaurante;
import jakarta.servlet.http.HttpServletRequest;


@CrossOrigin
@RestController
@RequestMapping("/restaurantes")
public class RestaurantesController {

	@Autowired
    private RestauranteService restauranteService;

    // Lista "crua" em JSON normal
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurante>> listarTodos() {
        List<Restaurante> lista = restauranteService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    // Buscar restaurante por ID em JSON normal
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable("id") String id) {
        Restaurante restaurante = restauranteService.buscarPorId(Integer.parseInt(id));
        return ResponseEntity.status(HttpStatus.OK).body(restaurante);
    }

    // Lista semântica (JSON-LD)
    @GetMapping(value = "/semantica", produces = "application/ld+json")
    public ResponseEntity<?> listarRestaurantesSemantica(HttpServletRequest request) {
        List<Map<String, Object>> ontologia = new ArrayList<>();
        ontologia = restauranteService.gerarOntologia(request);
        return ResponseEntity.status(HttpStatus.OK).body(ontologia);
    }

    // Um restaurante específico em JSON-LD
    @GetMapping(value = "/semantica/{id}", produces = "application/ld+json")
    public ResponseEntity<?> restauranteSemanticaPorId(@PathVariable("id") String id,
                                                       HttpServletRequest request) {
        Map<String, Object> ontologia =
                restauranteService.gerarOntologiaPorId(request, Integer.parseInt(id));
        return ResponseEntity.status(HttpStatus.OK).body(ontologia);
    }
}
