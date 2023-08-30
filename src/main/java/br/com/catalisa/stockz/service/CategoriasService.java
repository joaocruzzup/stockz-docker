package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.model.Categorias;
import br.com.catalisa.stockz.model.dto.CategoriasDTO;
import br.com.catalisa.stockz.repository.CategoriasRepository;
import br.com.catalisa.stockz.utils.mapper.CategoriasMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {
    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private CategoriasMapper categoriasMapper;

    public List<CategoriasDTO> listarTodos(){

        List<Categorias> categoriasList = categoriasRepository.findAll();
        List<CategoriasDTO> categoriasDTOList = new ArrayList<>();
        for (Categorias categorias: categoriasList) {
            CategoriasDTO categoriasDTO = categoriasMapper.toCategoriasDto(categorias);
            categoriasDTOList.add(categoriasDTO);
        }

        return categoriasDTOList;
    }

    public CategoriasDTO listarPorId(Long id) throws Exception {

        Optional<Categorias> categoriasOptional = categoriasRepository.findById(id);

        if (categoriasOptional.isEmpty()){
            throw new Exception("Categoria não encontrada");
        }
        Categorias categorias = categoriasOptional.get();
        CategoriasDTO categoriasDTO = categoriasMapper.toCategoriasDto(categorias);

        return categoriasDTO;
    }

    public CategoriasDTO criar(CategoriasDTO categoriasDTO){
        Categorias categorias = categoriasMapper.toCategorias(categoriasDTO);
        categoriasRepository.save(categorias);
        return categoriasDTO;
    }

    public CategoriasDTO atualizar(Long id, CategoriasDTO categoriasDTO) throws Exception {

        Optional<Categorias> categoriasOptional = categoriasRepository.findById(id);
        if (categoriasOptional.isEmpty()){
            throw new Exception("Categoria não encontrada");
        }
        Categorias categorias = categoriasOptional.get();
        CategoriasDTO categoriasDTORetorno = categoriasMapper.toCategoriasDto(categorias);

        if (categoriasDTO.getNome() != null){
            categoriasDTORetorno.setNome(categoriasDTO.getNome());
        }

        return categoriasDTORetorno;
    }

    public void deletar(Long id) throws Exception {
        Optional<Categorias> categoriasOptional = categoriasRepository.findById(id);
        if (categoriasOptional.isEmpty()){
            throw new Exception("Categoria não encontrada");
        }
        Categorias categorias = categoriasOptional.get();
        categoriasRepository.delete(categorias);
    }

}
