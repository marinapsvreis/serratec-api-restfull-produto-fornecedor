package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria", description = "endpoints")
public class CategoriaController {
	@Autowired
	CategoriaService categoriaService;

	@GetMapping
	@Operation(summary = "Listar todas as categorias")
	public ResponseEntity<List<Categoria>> findAllCategoria() {
		List<Categoria> categoriaList = categoriaService.findAllCategoria();
		return new ResponseEntity<>(categoriaList, HttpStatus.OK);
	}

	@GetMapping("/dto/{idCategoria}")
	@Operation(summary = "Listar categoria via ID via DTO")
	public ResponseEntity<CategoriaDTO> findCategoriaDTOById(@PathVariable Integer idCategoria) {
		CategoriaDTO categoriaDTO = categoriaService.findCategoriaDTOById(idCategoria);
		return new ResponseEntity<>(categoriaDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{idCategoria}")
	@Operation(summary = "Listar categoria via ID")
	public ResponseEntity<Categoria> findCategoriaById(@PathVariable Integer idCategoria) {
		Categoria categoria = categoriaService.findCategoriaById(idCategoria);
		if(null == categoria)
			throw new NoSuchElementFoundException("Não foi encontrado Categoria com o id " + idCategoria);
		else
			return new ResponseEntity<>(categoria, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Cadastrar categoria")
	public ResponseEntity<Categoria> saveCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria novoCategoria = categoriaService.saveCategoria(categoria);
		return new ResponseEntity<>(novoCategoria, HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	@Operation(summary = "Cadastrar categoria via DTO")
	public ResponseEntity<CategoriaDTO> saveCategoriaDTO(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		CategoriaDTO novoCategoriaDTO = categoriaService.saveCategoriaDTO(categoriaDTO);
		return new ResponseEntity<>(novoCategoriaDTO, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/com-foto", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@Operation(summary = "Cadastrar categoria com imagem")
	public ResponseEntity<Categoria> saveCategoriaComFoto(@RequestPart("categoria") String categoria, @RequestPart("file") MultipartFile file) {
		
		Categoria novoCategoria = categoriaService.saveCategoriaComFoto(categoria, file);
		return new ResponseEntity<>(novoCategoria, HttpStatus.CREATED);
		
	}
	
	@PutMapping
	@Operation(summary = "Atualizar categoria, passando todos os campos")
	public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria novoCategoria = categoriaService.updateCategoria(categoria);
		return new ResponseEntity<>(novoCategoria, HttpStatus.OK);
	}

	@DeleteMapping("/{idCategoria}")
	@Operation(summary = "Deletar categoria via ID")
	public ResponseEntity<String> deleteCategoria(@PathVariable Integer idCategoria) {
		if(null == categoriaService.findCategoriaById(idCategoria))
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		
		categoriaService.deleteCategoria(idCategoria);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
