package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.ProdutoDTO;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.exceptions.NotNullException;
import com.residencia.comercio.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/produto")
@Validated
@Tag(name = "Produto", description = "endpoints")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	@Operation(summary = "Listar todos os produtos")
	public ResponseEntity<List<Produto>> findAllProduto(){
		List<Produto> produtoList = produtoService.findAllProduto();
		if(produtoList.isEmpty()) {
			throw new NoSuchElementFoundException("Não foram encontrados Produtos");
		}else {
			return new ResponseEntity<>(produtoList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{idProduto}")
	@Operation(summary = "Listar produto via ID Path")
	public ResponseEntity<Produto> findProdutoById(@PathVariable Integer idProduto){
		Produto produto = produtoService.findProdutoById(idProduto);
		if(produto == null) {
			throw new NoSuchElementFoundException("Não foi encontrado Produto com o id: " + idProduto);
		}else {
			return new ResponseEntity<>(produto, HttpStatus.OK);
		}
	}
	
	@GetMapping("/id")
	@Operation(summary = "Listar produto via ID Request Params")
	public ResponseEntity<Produto> findProdutoById2(@RequestParam Integer idProduto){
		Produto produto = produtoService.findProdutoById(idProduto);
		if(produto == null) {
			throw new NoSuchElementFoundException("Não foi encontrado Produto com o id: " + idProduto);
		}else {
			return new ResponseEntity<>(produto, HttpStatus.OK);
		}
	}
	
	@GetMapping("/query")
	public ResponseEntity<Produto> findByIdQuery(
			@RequestParam
			@NotBlank(message = "O sku deve ser preenchido.")
			String sku){
		return new ResponseEntity<>(null, HttpStatus.CONTINUE);
	}
	
		
	@GetMapping("/request")
	public ResponseEntity<Produto> findByIdRequest(
			@RequestParam
			@NotBlank(message = "O id deve ser preenchido.")
			Integer id){
		return new ResponseEntity<>(null, HttpStatus.CONTINUE);
	}
	
	@GetMapping("/dto/{id}")
	@Operation(summary = "Listar produto via ID pelo DTO")
    public ResponseEntity<ProdutoDTO> findProdutoDTOById(@PathVariable Integer id) {
        return new ResponseEntity<>(produtoService.findProdutoDTOById(id), HttpStatus.OK);
    }
	
	@PostMapping
	@Operation(summary = "Cadastrar produto")
	public ResponseEntity<Produto> saveProduto(@Valid @RequestBody Produto produto){
		if(produto.getFornecedor().getIdFornecedor() == null) {
			throw new NotNullException("Id do Fornecedor não pode ser nulo");
		}
		
		if(produto.getCategoria().getIdCategoria() == null) {
			throw new NotNullException("Id da Categoria não pode ser nulo");
		}
		
		return new ResponseEntity<>(produtoService.saveProduto(produto), HttpStatus.CREATED);
	}
	
	@PostMapping("/dto")
	@Operation(summary = "Cadastrar produto via DTO")
    public ResponseEntity<ProdutoDTO> saveProdutoDTO(@Valid @RequestBody ProdutoDTO produtoDTO) {
        produtoService.saveProdutoDTO(produtoDTO);
        return new ResponseEntity<>(produtoDTO, HttpStatus.CREATED);
    }
	
	@PutMapping
	@Operation(summary = "Atualizar produto passando todos os dados.")
	public ResponseEntity<String> updateProduto(@Valid @RequestBody Produto produto){
		produtoService.updateProduto(produto);
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@DeleteMapping("/{idProduto}")
	@Operation(summary = "Deletar produto via ID")
	public ResponseEntity<String> deleteProduto(@PathVariable Integer idProduto){
		produtoService.deleteProduto(idProduto);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
