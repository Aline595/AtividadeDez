package com.example.controller.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.model.beans.Cidade;
import com.example.model.repository.CidadeRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {
	
	@Autowired
	private CidadeRepository cidRepo;

	//mostrar todas as cidades 
	@GetMapping("/lista")
	public List<Cidade> todasAsCidade(){
		return cidRepo.findAll();
	}

	//mostrar cidade de acordo com o nome
	@GetMapping(path = {"/{letra}"})
	public List<Cidade> CidadeComLetraInicial(@PathVariable String letra){
		
		return cidRepo.findByNomeStartingWith(letra);
	}
	
	//obter cidade pela latitude e longitude
	@GetMapping(path ="/buscalatitudelongitude/{latitude}/{longitude}")
	public List<Cidade> CidadeComLatitudeLongitude(@PathVariable int latitude, @PathVariable int longitude){
		
		return cidRepo.findByLatitudeAndLongitude(latitude, longitude);
	}
	
	//cadastrar nova cidade
	@PostMapping("/salvar")
	@ResponseStatus (HttpStatus.CREATED)
	public ResponseEntity<Cidade> salvar (@RequestBody Cidade cidade, HttpServletResponse response){
		try {
			Cidade cidadeCriada = cidRepo.save(cidade);
			URI uri = ServletUriComponentsBuilder.
					fromCurrentServletMapping().path("/{id}").
					buildAndExpand(cidadeCriada.getId()).toUri();
			response.setHeader("Location", uri.toASCIIString());
			return ResponseEntity.created(uri).body(cidadeCriada);
		}catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
}
