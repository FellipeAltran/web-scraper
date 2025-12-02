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
import br.edu.utfpr.td.tsi.web.scraper.ontologia.service.ImovelService;
import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Imovel;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/imoveis")
public class ImoveisController {

	@Autowired
	private ImovelService imovelService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Imovel>> listarTodos() {
		List<Imovel> passagens = imovelService.listarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(passagens);
	}
	
	@GetMapping(value = "/semantica", produces = "application/ld+json")
	public ResponseEntity<?> listarImoveisSemanticas(HttpServletRequest request) {
		List<Map<String, Object>> ontologia = new ArrayList<>();

		ontologia = imovelService.gerarOntologia(request);

		return ResponseEntity.status(HttpStatus.OK).body(ontologia);
	}

	@GetMapping(value = "/semantica/{id}", produces = "application/ld+json")
	public ResponseEntity<?> imovelSemanticaPorId(@PathVariable("id") String id, HttpServletRequest request) {
		Map<String, Object> ontologia = imovelService.gerarOntologiaPorId(request, Integer.parseInt(id));

		return ResponseEntity.status(HttpStatus.OK).body(ontologia);
	}
	
}
