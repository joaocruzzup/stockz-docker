package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.CategoriasDTO;
import br.com.catalisa.stockz.repository.CategoriasRepository;
import br.com.catalisa.stockz.repository.ProdutosRepository;
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
    private ProdutosRepository produtosRepository;

    @Autowired
    private CategoriasMapper categoriasMapper;

    public List<CategoriasDTO> listarTodos(){

        List<Categoria> categoriaList = categoriasRepository.findAll();
        List<CategoriasDTO> categoriasDTOList = new ArrayList<>();
        for (Categoria categoria : categoriaList) {
            CategoriasDTO categoriasDTO = categoriasMapper.toCategoriasDto(categoria);
            categoriasDTOList.add(categoriasDTO);
        }

        return categoriasDTOList;
    }

    public CategoriasDTO listarPorId(Long id) throws Exception {
        Categoria categoriaEncontrada = buscarCategoriaPorId(id);
        CategoriasDTO categoriasDTO = categoriasMapper.toCategoriasDto(categoriaEncontrada);
        return categoriasDTO;
    }

    public CategoriasDTO criar(CategoriasDTO categoriasDTO){
        Categoria categoria = categoriasMapper.toCategorias(categoriasDTO);
        categoriasRepository.save(categoria);
        return categoriasDTO;
    }

    public CategoriasDTO atualizar(Long id, CategoriasDTO categoriasDTO) throws Exception {
        Categoria categoriaEncontrada = buscarCategoriaPorId(id);

        if (categoriasDTO.getNome() != null){
            categoriaEncontrada.setNome(categoriasDTO.getNome());
        }

        categoriasRepository.save(categoriaEncontrada);

        return categoriasMapper.toCategoriasDto(categoriaEncontrada);
    }

    public void deletar(Long id) throws Exception {
        Categoria categoriaEncontrada = buscarCategoriaPorId(id);

        Optional<Produto> produtosOptional = produtosRepository.findByCategoria(categoriaEncontrada);
        if (produtosOptional.isPresent()){
            throw new Exception(("Categoria atrelada a um produto. Atualize primeiro a categoria do produto"));
        }

        categoriasRepository.delete(categoriaEncontrada);
    }

    private Categoria buscarCategoriaPorId(Long id) throws EntidadeNaoEncontradaException {
        Optional<Categoria> categoriasOptional = categoriasRepository.findById(id);
        if (categoriasOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Categoria não encontrada");
        }
        return categoriasOptional.get();
    }

}
