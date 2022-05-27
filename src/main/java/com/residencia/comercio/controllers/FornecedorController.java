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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.CadastroEmpresaReceitaDTO;
import com.residencia.comercio.dtos.FornecedorDTO;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.FornecedorService;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {
	@Autowired
	FornecedorService fornecedorService;

	@GetMapping
	public ResponseEntity<List<Fornecedor>> findAllFornecedor() {
		List<Fornecedor> fornecedorList = fornecedorService.findAllFornecedor();
		return new ResponseEntity<>(fornecedorList, HttpStatus.OK);
	}
	
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<CadastroEmpresaReceitaDTO> consultaDaCadastroEmpresaReceitaDTO(String cnpj) {
		CadastroEmpresaReceitaDTO cadEmpresaDTO = fornecedorService.consultarDadosPorCnpj(cnpj);
		if(cadEmpresaDTO == null) {
			throw new NoSuchElementFoundException("Não foram encontrados dados para o CNPJ informado!");
		}else {
			return new ResponseEntity<>(cadEmpresaDTO, HttpStatus.OK);
		}
	}

	@GetMapping("/dto/{idFornecedor}")
	public ResponseEntity<FornecedorDTO> findFornecedorDTOById(@PathVariable Integer idFornecedor) {
		FornecedorDTO fornecedorDTO = fornecedorService.findFornecedorDTOById(idFornecedor);
		return new ResponseEntity<>(fornecedorDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{idFornecedor}")
	public ResponseEntity<Fornecedor> findFornecedorById(@PathVariable Integer idFornecedor) {
		Fornecedor fornecedor = fornecedorService.findFornecedorById(idFornecedor);
		if(null == fornecedor)
			throw new NoSuchElementFoundException("Não foi encontrado Fornecedor com o id " + idFornecedor);
		else
			return new ResponseEntity<>(fornecedor, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Fornecedor> saveFornecedor(@RequestParam String cnpj) {
		Fornecedor fornecedor = new Fornecedor();
		Fornecedor novoFornecedor = fornecedorService.saveFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
	}

	@PostMapping("/completo")
	public ResponseEntity<Fornecedor> saveFornecedorCompleto(@RequestBody Fornecedor fornecedor) {
		Fornecedor novoFornecedor = fornecedorService.saveFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
	}
	
	@PostMapping("/dto")
	public ResponseEntity<FornecedorDTO> saveFornecedorDTO(@RequestBody FornecedorDTO fornecedorDTO) {
		FornecedorDTO novoFornecedorDTO = fornecedorService.saveFornecedorDTO(fornecedorDTO);
		return new ResponseEntity<>(novoFornecedorDTO, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Fornecedor> updateFornecedor(@RequestBody Fornecedor fornecedor) {
		Fornecedor novoFornecedor = fornecedorService.updateFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.OK);
	}

	@DeleteMapping("/{idFornecedor}")
	public ResponseEntity<String> deleteFornecedor(@PathVariable Integer idFornecedor) {
		if(null == fornecedorService.findFornecedorById(idFornecedor))
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		
		fornecedorService.deleteFornecedor(idFornecedor);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
