package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	public ResponseEntity<List<Produto>> findAllProduto(){
		List<Produto> produtoList = produtoService.findAllProduto();
		if(produtoList.isEmpty()) {
			throw new NoSuchElementFoundException("Não foram encontrados Produtos");
		}else {
			return new ResponseEntity<>(produtoList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{idProduto}")
	public ResponseEntity<Produto> findProdutoById(@PathVariable Integer idProduto){
		Produto produto = produtoService.findProdutoById(idProduto);
		if(produto == null) {
			throw new NoSuchElementFoundException("Não foi encontrado Produto com o id: " + idProduto);
		}else {
			return new ResponseEntity<>(produto, HttpStatus.OK);
		}
	}
	
	@GetMapping("/id")
	public ResponseEntity<Produto> findProdutoById2(@RequestParam Integer idProduto){
		Produto produto = produtoService.findProdutoById(idProduto);
		if(produto == null) {
			throw new NoSuchElementFoundException("Não foi encontrado Produto com o id: " + idProduto);
		}else {
			return new ResponseEntity<>(produto, HttpStatus.OK);
		}
	}
	
	@PostMapping
	public ResponseEntity<Produto> saveProduto(@Valid @RequestBody Produto produto){
		return new ResponseEntity<>(produtoService.saveProduto(produto), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<String> updateProduto(@RequestBody Produto produto){
		produtoService.updateProduto(produto);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@PutMapping("/{idProduto}")
	public ResponseEntity<Produto> updateProduto(@PathVariable Integer idProduto, @RequestBody Produto produto){
		Produto produtoAtualizado = produtoService.updateProdutoComId(produto, idProduto);
		if(produto == null) {
			return new ResponseEntity<>(produtoAtualizado, HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{idProduto}")
	public ResponseEntity<String> deleteProduto(@PathVariable Integer idProduto){
		produtoService.deleteProduto(idProduto);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}
