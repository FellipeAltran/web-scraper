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

import br.edu.utfpr.td.tsi.web.scraper.raspagem.modelos.Noticia;
import jakarta.servlet.http.HttpServletRequest;
import br.edu.utfpr.td.tsi.web.scraper.ontologia.service.NoticiaService;

@CrossOrigin
@RestController
@RequestMapping("/noticias")
public class NoticiasController {

	@Autowired
	private NoticiaService noticiaService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Noticia>> listarTodos() {
		List<Noticia> passagens = noticiaService.listarTodas();
		return ResponseEntity.status(HttpStatus.OK).body(passagens);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Noticia> buscarPorId(@PathVariable("id") String id) {
		Noticia noticia = noticiaService.buscarPorId(Integer.parseInt(id));
		return ResponseEntity.status(HttpStatus.OK).body(noticia);
	}

	@GetMapping(value = "/semantica", produces = "application/ld+json")
	public ResponseEntity<?> listarNoticiasSemanticas(HttpServletRequest request) {
		List<Map<String, Object>> ontologia = new ArrayList<>();

		ontologia = noticiaService.gerarOntologia(request);

		return ResponseEntity.status(HttpStatus.OK).body(ontologia);
	}

	@GetMapping(value = "/semantica/{id}", produces = "application/ld+json")
	public ResponseEntity<?> noticiaSemanticaPorId(@PathVariable("id") String id, HttpServletRequest request) {
		Map<String, Object> ontologia = noticiaService.gerarOntologiaPorId(request, Integer.parseInt(id));

		return ResponseEntity.status(HttpStatus.OK).body(ontologia);
	}

}