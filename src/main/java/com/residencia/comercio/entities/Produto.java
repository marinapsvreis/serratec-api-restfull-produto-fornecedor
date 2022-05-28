package com.residencia.comercio.entities;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
 
@Entity
@Table(name = "produto")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProduto")
public class Produto {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produto")
	private Integer idProduto;
 
	@NotBlank(message = "O sku do produto não pode estar vazio")
	@Column(name= "sku")
	private String sku;
 
	@Column(name = "nome_produto")
	@NotEmpty(message = "O nome do produto não pode ficar em branco.")
	private String nomeProduto;
 
	@NotNull(message = "Escolha um fornecedor para esse produto")
	@ManyToOne
	@JoinColumn(name = "id_fornecedor", referencedColumnName = "id_fornecedor")	
	private Fornecedor fornecedor;
 
	@NotNull(message = "Escolha uma categoria para esse produto")
	@ManyToOne
	@JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
	private Categoria categoria;
 
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
 
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
 
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
 
	public Categoria getCategoria() {
		return categoria;
	}
 
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}