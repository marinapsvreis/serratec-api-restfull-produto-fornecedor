package com.residencia.comercio.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.residencia.comercio.dtos.CadastroEmpresaReceitaDTO;
import com.residencia.comercio.dtos.ConsultaCEPDTO;
import com.residencia.comercio.dtos.FornecedorDTO;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.repositories.FornecedorRepository;

@Service
public class FornecedorService {
	@Autowired
	FornecedorRepository fornecedorRepository;

	public List<Fornecedor> findAllFornecedor() {
		return fornecedorRepository.findAll();
	}

	public Fornecedor findFornecedorById(Integer id) {
		return fornecedorRepository.findById(id).isPresent() ? fornecedorRepository.findById(id).get() : null;
	}

	public FornecedorDTO findFornecedorDTOById(Integer id) {
		Fornecedor fornecedor = fornecedorRepository.findById(id).isPresent() ? fornecedorRepository.findById(id).get()
				: null;

		FornecedorDTO fornecedorDTO = new FornecedorDTO();
		if (null != fornecedor) {
			fornecedorDTO = converterEntidadeParaDto(fornecedor);
		}
		return fornecedorDTO;
	}

	public Fornecedor saveFornecedor(Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}

	public FornecedorDTO saveFornecedorDTO(FornecedorDTO fornecedorDTO) {

		Fornecedor fornecedor = new Fornecedor();

		fornecedor.setIdFornecedor(fornecedorDTO.getIdFornecedor());
		Fornecedor novoFornecedor = fornecedorRepository.save(fornecedor);

		return converterEntidadeParaDto(novoFornecedor);
	}

	public Fornecedor updateFornecedor(Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}

	public void deleteFornecedor(Integer id) {
		Fornecedor inst = fornecedorRepository.findById(id).get();
		fornecedorRepository.delete(inst);
	}

	public void deleteFornecedor(Fornecedor fornecedor) {
		fornecedorRepository.delete(fornecedor);
	}

	private Fornecedor convertDTOToEntidade(FornecedorDTO fornecedorDTO) {
		Fornecedor fornecedor = new Fornecedor();

		fornecedor.setIdFornecedor(fornecedorDTO.getIdFornecedor());
		return fornecedor;
	}

	private FornecedorDTO converterEntidadeParaDto(Fornecedor fornecedor) {
		FornecedorDTO fornecedorDTO = new FornecedorDTO();
		fornecedorDTO.setIdFornecedor(fornecedor.getIdFornecedor());
		return fornecedorDTO;
	}

	public CadastroEmpresaReceitaDTO consultarDadosPorCnpj(String cnpj) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://www.receitaws.com.br/v1/cnpj/{cnpj}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("cnpj", cnpj);

		CadastroEmpresaReceitaDTO cadastroEmpresaReceitaDTO = restTemplate.getForObject(uri,
				CadastroEmpresaReceitaDTO.class, params);

		return cadastroEmpresaReceitaDTO;
	}

	public Fornecedor converterAPIExternaToEntidade(CadastroEmpresaReceitaDTO dto) {
		Date data = new Date();
		try {
			data = new SimpleDateFormat("dd/MM/yyyy").parse(dto.getAbertura());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setBairro(dto.getBairro());
		fornecedor.setCep(dto.getCep());
		fornecedor.setCnpj(dto.getCnpj());
		fornecedor.setComplemento(dto.getComplemento());
		fornecedor.setDataAbertura(data);
		fornecedor.setEmail(dto.getEmail());
		fornecedor.setLogradouro(dto.getLogradouro());
		fornecedor.setMunicipio(dto.getMunicipio());
		fornecedor.setNomeFantasia(dto.getFantasia());
		fornecedor.setNumero(dto.getNumero());
		fornecedor.setRazaoSocial(dto.getNome());
		fornecedor.setStatusSituacao(dto.getSituacao());
		fornecedor.setTelefone(dto.getTelefone());
		fornecedor.setTipo(dto.getTipo());
		fornecedor.setUf(dto.getUf());

		return fornecedor;
	}

	public ConsultaCEPDTO receberEnderecoViaCEPAPI(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://viacep.com.br/ws/{cep}/json/";

		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);

		ConsultaCEPDTO consultaCEPDTO = restTemplate.getForObject(uri, ConsultaCEPDTO.class, params);

		return consultaCEPDTO;
	}

	public Fornecedor atualizarEnderecoFornecedor(Fornecedor fornecedor, ConsultaCEPDTO dto) {
		fornecedor.setComplemento(dto.getComplemento());
		fornecedor.setLogradouro(dto.getLogradouro());
		fornecedor.setBairro(dto.getBairro());
		fornecedor.setMunicipio(dto.getLocalidade());
		fornecedor.setUf(dto.getUf());
		fornecedor.setCep(dto.getCep());

		return fornecedor;
	}
	
	public boolean CNPJValid(String cnpj) {
        if (cnpj.charAt(2) != '.' || cnpj.charAt(6) != '.' || cnpj.charAt(10) != '/' || cnpj.charAt(15) != '-' || cnpj.length() != 18) {
            return false;
        }
        else {
            return true;
        }
    }


}
