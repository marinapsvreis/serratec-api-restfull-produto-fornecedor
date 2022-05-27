package com.residencia.comercio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	@Autowired
	CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<Categoria>> findAllCategoria() {
		List<Categoria> categoriaList = categoriaService.findAllCategoria();
		return new ResponseEntity<>(categoriaList, HttpStatus.OK);
	}

	@GetMapping("/dto/{idCategoria}")
	public ResponseEntity<CategoriaDTO> findCategoriaDTOById(@PathVariable Integer idCategoria) {
		CategoriaDTO categoriaDTO = categoriaService.findCategoriaDTOById(idCategoria);
		return new ResponseEntity<>(categoriaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{idCategoria}")
	public ResponseEntity<Categoria> findCategoriaById(@PathVariable Integer idCategoria) {
		Categoria categoria = categoriaService.findCategoriaById(idCategoria);
		if(null == categoria)
			throw new NoSuchElementFoundException("Não foi encontrado Categoria com o id " + idCategoria);
		else
			return new ResponseEntity<>(categoria, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria) {
		Categoria novoCategoria = categoriaService.saveCategoria(categoria);
		return new ResponseEntity<>(novoCategoria, HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	public ResponseEntity<CategoriaDTO> saveCategoriaDTO(@RequestBody CategoriaDTO categoriaDTO) {
		CategoriaDTO novoCategoriaDTO = categoriaService.saveCategoriaDTO(categoriaDTO);
		return new ResponseEntity<>(novoCategoriaDTO, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Categoria> updateCategoria(@RequestBody Categoria categoria) {
		Categoria novoCategoria = categoriaService.updateCategoria(categoria);
		return new ResponseEntity<>(novoCategoria, HttpStatus.OK);
	}

	@DeleteMapping("/{idCategoria}")
	public ResponseEntity<String> deleteCategoria(@PathVariable Integer idCategoria) {
		if(null == categoriaService.findCategoriaById(idCategoria))
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		
		categoriaService.deleteCategoria(idCategoria);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
