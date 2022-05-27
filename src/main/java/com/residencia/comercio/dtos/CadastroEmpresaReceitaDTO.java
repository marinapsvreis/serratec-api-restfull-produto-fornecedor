package com.residencia.comercio.dtos;

public class CadastroEmpresaReceitaDTO {
	private String situacao;
	private String tipo;
	private String nome;
	private String abertura;
	private String telefone;
	private String email;
	private String cnpj;
	private String fantasia;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String cep;
	private String bairro;
	private String municipio;
	private String uf;

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAbertura() {
		return abertura;
	}

	public void setAbertura(String abertura) {
		this.abertura = abertura;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return "CadastroEmpresaReceitaDTO [situacao=" + situacao + ", tipo=" + tipo + ", nome=" + nome + ", abertura="
				+ abertura + ", telefone=" + telefone + ", email=" + email + ", cnpj=" + cnpj + ", fantasia=" + fantasia
				+ ", logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento + ", cep=" + cep
				+ ", bairro=" + bairro + ", municipio=" + municipio + ", uf=" + uf + "]";
	}

}
