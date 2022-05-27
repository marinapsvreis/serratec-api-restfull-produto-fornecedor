package com.residencia.comercio.dtos;
 
public class ProdutoDTO {
	private Integer idProduto;
	private String sku;
	private String nomeProduto;
	private Integer fornecedorId;
	private String fornecedorNome;
	private Integer categoriaId;
	private String categoriaNome;
 
	public Integer getIdProduto() {
		return idProduto;
	}
 
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
 
	public String getSku() {
		return sku;
	}
 
	public void setSku(String sku) {
		this.sku = sku;
	}
 
	public String getNomeProduto() {
		return nomeProduto;
	}
 
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
 
	public Integer getFornecedorId() {
		return fornecedorId;
	}
 
	public void setFornecedorId(Integer fornecedorId) {
		this.fornecedorId = fornecedorId;
	}
 
	public String getFornecedorNome() {
		return fornecedorNome;
	}
 
	public void setFornecedorNome(String fornecedorNome) {
		this.fornecedorNome = fornecedorNome;
	}
 
	public Integer getCategoriaId() {
		return categoriaId;
	}
 
	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}
 
	public String getCategoriaNome() {
		return categoriaNome;
	}
 
	public void setCategoriaNome(String categoriaNome) {
		this.categoriaNome = categoriaNome;
	}
 
}