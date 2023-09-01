package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.CategoriaJaCadastradaException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import br.com.catalisa.stockz.model.dto.DelecaoResponse;
import br.com.catalisa.stockz.repository.CategoriaRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import br.com.catalisa.stockz.utils.mapper.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriasRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> listarTodos(){

        List<Categoria> categoriaList = categoriasRepository.findAll();
        List<CategoriaDTO> categoriaDTOList = new ArrayList<>();
        for (Categoria categoria : categoriaList) {
            CategoriaDTO categoriaDTO = categoriaMapper.toCategoriasDto(categoria);
            categoriaDTOList.add(categoriaDTO);
        }

        return categoriaDTOList;
    }

    public CategoriaDTO listarPorId(Long id) throws Exception {
        Categoria categoriaEncontrada = buscarCategoriaPorId(id);
        CategoriaDTO categoriaDTO = categoriaMapper.toCategoriasDto(categoriaEncontrada);
        return categoriaDTO;
    }

    public CategoriaDTO criar(CategoriaDTO categoriaDTO){
        Categoria categoria = categoriaMapper.toCategorias(categoriaDTO);
        buscarCategoriaPorNome(categoriaDTO.getNome());
        categoriasRepository.save(categoria);
        return categoriaDTO;
    }

    public CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDTO) throws Exception {
        Categoria categoriaEncontrada = buscarCategoriaPorId(id);

        if (categoriaDTO.getNome() != null){
            categoriaEncontrada.setNome(categoriaDTO.getNome());
        }

        categoriasRepository.save(categoriaEncontrada);

        return categoriaMapper.toCategoriasDto(categoriaEncontrada);
    }

    public DelecaoResponse deletar(Long id) throws Exception {
        Categoria categoriaEncontrada = buscarCategoriaPorId(id);

        Optional<Produto> produtosOptional = produtoRepository.findByCategoria(categoriaEncontrada);
        if (produtosOptional.isPresent()){
            throw new Exception(("Não é possível deletar categoria atrelada a um produto. "));
        }

        categoriasRepository.delete(categoriaEncontrada);
        return new DelecaoResponse("Categoria deletada com sucesso");
    }

    private Categoria buscarCategoriaPorId(Long id) throws EntidadeNaoEncontradaException {
        Optional<Categoria> categoriasOptional = categoriasRepository.findById(id);
        if (categoriasOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Categoria não encontrada");
        }
        return categoriasOptional.get();
    }

    private void buscarCategoriaPorNome(String nome) throws EntidadeNaoEncontradaException {
        Optional<Categoria> categoriasOptional = categoriasRepository.findByNome(nome);
        if (categoriasOptional.isPresent()) {
            throw new CategoriaJaCadastradaException("Categoria com esse nome já está cadastrada");
        }
    }

}
