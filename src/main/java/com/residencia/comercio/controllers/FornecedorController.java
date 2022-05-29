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

import com.residencia.comercio.dtos.CadastroEmpresaReceitaDTO;
import com.residencia.comercio.dtos.FornecedorDTO;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.exceptions.CNPJException;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.exceptions.NotNullException;
import com.residencia.comercio.services.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/fornecedor")
@Tag(name = "Fornecedor", description = "endpoints")
public class FornecedorController {
	@Autowired
	FornecedorService fornecedorService;

	@GetMapping
	@Operation(summary = "Listar todos os fornecedores")
	public ResponseEntity<List<Fornecedor>> findAllFornecedor() {
		List<Fornecedor> fornecedorList = fornecedorService.findAllFornecedor();
		return new ResponseEntity<>(fornecedorList, HttpStatus.OK);
	}
	
	@GetMapping("/cnpj/{cnpj}")
	@Operation(summary = "Consulta cadastro CNPJ na Receita")
	public ResponseEntity<CadastroEmpresaReceitaDTO> consultaDaCadastroEmpresaReceitaDTO(String cnpj) {
		CadastroEmpresaReceitaDTO cadEmpresaDTO = fornecedorService.consultarDadosPorCnpj(cnpj);
		if(cadEmpresaDTO == null) {
			throw new NoSuchElementFoundException("Não foram encontrados dados para o CNPJ informado!");
		}else {
			return new ResponseEntity<>(cadEmpresaDTO, HttpStatus.OK);
		}
	}

	@GetMapping("/dto/{idFornecedor}")
	@Operation(summary = "Listar um fornecedor pelo ID via DTO")
	public ResponseEntity<FornecedorDTO> findFornecedorDTOById(@PathVariable Integer idFornecedor) {
		FornecedorDTO fornecedorDTO = fornecedorService.findFornecedorDTOById(idFornecedor);
		return new ResponseEntity<>(fornecedorDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{idFornecedor}")
	@Operation(summary = "Listar um fornecedor via ID")
	public ResponseEntity<Fornecedor> findFornecedorById(@PathVariable Integer idFornecedor) {
		Fornecedor fornecedor = fornecedorService.findFornecedorById(idFornecedor);
		if(null == fornecedor)
			throw new NoSuchElementFoundException("Não foi encontrado Fornecedor com o id " + idFornecedor);
		else
			return new ResponseEntity<>(fornecedor, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Cadastrar um fornecedor via API Receita")
    public ResponseEntity<Fornecedor> saveFornecedor(@RequestParam String cnpj) {
		if(cnpj == null) {
			throw new NotNullException("CNPJ não pode ser nulo");
		}
		
		if(cnpj.length() != 14) {
			throw new CNPJException("CNPJ deve conter 14 digitos (sem pontos, traços ou barras)");
		}
		
        return new ResponseEntity<>(fornecedorService.saveFornecedor(fornecedorService
        		.converterAPIExternaToEntidade(fornecedorService
        		.consultarDadosPorCnpj(cnpj))), HttpStatus.CREATED);
    }

	@PostMapping("/completo")
	@Operation(summary = "Cadastrar fornecedor, informando todos os dados")
	public ResponseEntity<Fornecedor> saveFornecedorCompleto(@Valid @RequestBody Fornecedor fornecedor) {
		
		if(!fornecedorService.CNPJValid(fornecedor.getCnpj())) {
			throw new CNPJException("CNPJ deve conter 18 digitos (com pontos, traços e barras)");
		}
		
		Fornecedor novoFornecedor = fornecedorService.saveFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
	}
	
	@PostMapping("/dto")
	@Operation(summary = "Cadastrar fornecedor via DTO")
	public ResponseEntity<FornecedorDTO> saveFornecedorDTO(@Valid @RequestBody FornecedorDTO fornecedorDTO) {
		FornecedorDTO novoFornecedorDTO = fornecedorService.saveFornecedorDTO(fornecedorDTO);
		return new ResponseEntity<>(novoFornecedorDTO, HttpStatus.CREATED);
	}
	
	@PutMapping
	@Operation(summary = "Atualizar fornecedor, passando todos os campos.")
	public ResponseEntity<Fornecedor> updateFornecedor(@Valid @RequestBody Fornecedor fornecedor) {
		Fornecedor novoFornecedor = fornecedorService.updateFornecedor(fornecedor);
		return new ResponseEntity<>(novoFornecedor, HttpStatus.OK);
	}
	
	@PutMapping("/atualizar/{idFornecedor}")
	@Operation(summary = "Atualizar o endereço de um fornecedor via API ViaCEP")
	public ResponseEntity<Fornecedor> updateAddressFornecedor(@Valid @PathVariable Integer idFornecedor, @RequestParam String cep) {
		return new ResponseEntity<>(fornecedorService.updateFornecedor
				(fornecedorService.atualizarEnderecoFornecedor
				(fornecedorService.findFornecedorById(idFornecedor),fornecedorService.receberEnderecoViaCEPAPI(cep))),
				HttpStatus.OK);
	}

	@DeleteMapping("/{idFornecedor}")
	@Operation(summary = "Deletar fornecedor via id")
	public ResponseEntity<String> deleteFornecedor(@PathVariable Integer idFornecedor) {
		if(null == fornecedorService.findFornecedorById(idFornecedor))
			return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
		
		fornecedorService.deleteFornecedor(idFornecedor);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
