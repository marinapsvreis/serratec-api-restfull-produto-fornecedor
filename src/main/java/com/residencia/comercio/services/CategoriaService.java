package com.residencia.comercio.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private Arquivo2Service arquivo2Service;
	
	@Autowired
	private MailService emailService;
	
	@Value("${pasta.arquivos.imagem}")
    private Path path;
	
	public List<Categoria> findAllCategoria(){
		return categoriaRepository.findAll();
	}
	
	public Categoria findCategoriaById(Integer id) {
		return categoriaRepository.findById(id).isPresent() ?
				categoriaRepository.findById(id).get() : null;
	}

	public CategoriaDTO findCategoriaDTOById(Integer id) {
		Categoria categoria = categoriaRepository.findById(id).isPresent() ?
				categoriaRepository.findById(id).get() : null;
		
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		if(null != categoria) {
			categoriaDTO = converterEntidadeParaDto(categoria);
		}
		return categoriaDTO;
	}
	
	public Categoria saveCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public Categoria saveCategoriaComFoto(String categoriaString, MultipartFile file){
		
		Categoria novaCategoria = new Categoria();
		
		try {
			ObjectMapper objMapper = new ObjectMapper();
			novaCategoria = objMapper.readValue(categoriaString, Categoria.class);
		}catch(IOException e) {
			System.out.println("Ocorreu um erro na conversão");
		}
		
		Categoria categoriaSalva = categoriaRepository.save(novaCategoria);
		
		String fileName = "categoria." + categoriaSalva.getIdCategoria() + ".image.png";
		
		arquivo2Service.criarArquivo(fileName, file);		
		
		try {
			categoriaSalva.setNomeImagem(path.resolve(fileName).toRealPath().toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
//		String corpoEmail = "Foi cadastrada uma categoria com sucesso!" + categoriaSalva.toString();
//		emailService.enviarEmail("teste@teste.com", "Cadastro de Categoria", corpoEmail);
		
        try {
            emailService.enviarEmailHTML("teste@teste.com", "Teste Email Categoria com HTML!!!", "<h1>Olá mundo!</h1><br><p>Muito <b>fácil!</b></p>");
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar e-mail HTML.");
            e.printStackTrace();
        }

		return categoriaRepository.save(categoriaSalva);
	}
	
	public CategoriaDTO saveCategoriaDTO(CategoriaDTO categoriaDTO) {
			
		Categoria categoria = new Categoria();
		
		categoria.setIdCategoria(categoriaDTO.getIdCategoria());
		Categoria novoCategoria = categoriaRepository.save(categoria);
		
		return converterEntidadeParaDto(novoCategoria);
	}
	
	public Categoria updateCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public void deleteCategoria(Integer id) {
		Categoria inst = categoriaRepository.findById(id).get();
		categoriaRepository.delete(inst);
	}
	
	public void deleteCategoria(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}
	
	private Categoria convertDTOToEntidade(CategoriaDTO categoriaDTO){
		Categoria categoria = new Categoria();
		
		categoria.setIdCategoria(categoriaDTO.getIdCategoria());
		return categoria;
	}
		
	private CategoriaDTO converterEntidadeParaDto(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setIdCategoria(categoria.getIdCategoria());
		return categoriaDTO;
	}
}
