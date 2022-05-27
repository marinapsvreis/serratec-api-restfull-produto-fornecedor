package com.residencia.comercio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.repositories.CategoriaRepository;
import com.residencia.comercio.repositories.FornecedorRepository;
import com.residencia.comercio.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
 
	@Autowired
	FornecedorRepository fornecedorRepository;

	public List<Produto> findAllProduto(){
		return produtoRepository.findAll();
	}
	
	public Produto findProdutoById(Integer idProduto) {
		return produtoRepository.findById(idProduto).isPresent() ? 
				produtoRepository.findById(idProduto).get() 
				: null;
	}
	
	public Produto saveProduto(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto updateProduto(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto updateProdutoComId(Produto produto, Integer idProduto) {
		Produto produtoBD = produtoRepository.findById(idProduto).isPresent()
				? produtoRepository.findById(idProduto).get()
				: null;
		
		Produto produtoAtualizado = null;
		if(null != produtoBD) {
			produtoBD.setNomeProduto(produto.getNomeProduto());
			produtoBD.setSku(produto.getSku());
			produtoBD.setFornecedor(produto.getFornecedor());
			produtoBD.setCategoria(produto.getCategoria());

			produtoAtualizado = produtoRepository.save(produtoBD);
		}
		return produtoAtualizado;
	}
	
	public void deleteProduto(Integer idProduto) {		
		produtoRepository.deleteById(idProduto);
	}	
	
}
