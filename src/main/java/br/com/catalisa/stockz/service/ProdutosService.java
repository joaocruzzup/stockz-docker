package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.exception.AtributoNaoPreenchidoException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.ProdutosDTO;
import br.com.catalisa.stockz.repository.CategoriasRepository;
import br.com.catalisa.stockz.repository.ProdutosRepository;
import br.com.catalisa.stockz.utils.mapper.ProdutosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutosService {
    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private ProdutosMapper produtosMapper;

    public List<ProdutosDTO> listarTodos(){

        List<Produto> produtoList = produtosRepository.findAll();
        List<ProdutosDTO> produtosDTOList = new ArrayList<>();
        for (Produto produto : produtoList) {
            if (produto.getStatusProduto() == StatusProduto.ATIVO){
                ProdutosDTO produtosDTO = produtosMapper.toProdutosDTO(produto);
                produtosDTOList.add(produtosDTO);
            }
        }

        return produtosDTOList;
    }

    public ProdutosDTO listarPorId(Long id) {
        Produto produtoEncontrado = buscarProdutoPorId(id);
        ProdutosDTO produtosDTO = produtosMapper.toProdutosDTO(produtoEncontrado);
        return produtosDTO;
    }

    public ProdutosDTO listarPorNome(String nome){
        Produto produtoEncontrado = buscarProdutoPorNome(nome);
        ProdutosDTO produtosDTO = produtosMapper.toProdutosDTO(produtoEncontrado);
        return produtosDTO;
    }

    //ToDo tentar mudar a categoria
    public ProdutosDTO criar(ProdutosDTO produtosDTO) {
        Optional<Categoria> categoriaOptional = categoriasRepository.findById(produtosDTO.getCategoria().getId());
        if (categoriaOptional.isEmpty()){
            throw new AtributoNaoPreenchidoException("Atributo categoria preenchido incorretamente");
        }
        Produto produto = produtosMapper.toProdutos(produtosDTO);
        produtosRepository.save(produto);
        produtosDTO.setCategoria(categoriaOptional.get());
        return produtosDTO;
    }

    public ProdutosDTO atualizar(Long id, ProdutosDTO produtosDTO) {
        Produto produtoEncontrado = buscarProdutoPorId(id);

        if (produtosDTO.getNome() != null){
            produtoEncontrado.setNome(produtosDTO.getNome());
        }
        if (produtosDTO.getPreco() != null){
            produtoEncontrado.setPreco(produtosDTO.getPreco());
        }
        if (produtosDTO.getDescricao() != null){
            produtoEncontrado.setDescricao(produtosDTO.getDescricao());
        }
        if (produtosDTO.getCategoria().getId() != null){
            Optional<Categoria> categoriaOptional = categoriasRepository.findById(produtosDTO.getCategoria().getId());
            if (categoriaOptional.isEmpty()){
                throw new EntidadeNaoEncontradaException("Categoria não encontrada");
            }
            produtoEncontrado.setCategoria(categoriaOptional.get());
        }
        produtosRepository.save(produtoEncontrado);

        return produtosMapper.toProdutosDTO(produtoEncontrado);
    }

    public void deletar(Long id) {
        Optional<Produto> produtosOptional = produtosRepository.findById(id);
        if (produtosOptional.isEmpty()){
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        Produto produto = produtosOptional.get();
        produto.setStatusProduto(StatusProduto.INATIVO);
        produtosRepository.delete(produto);
    }

    private Produto buscarProdutoPorId(Long id) {
        Optional<Produto> produtosOptional = produtosRepository.findById(id);

        if (produtosOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        return produtosOptional.get();
    }

    private Produto buscarProdutoPorNome(String nome) {
        Optional<Produto> produtosOptional = produtosRepository.findByNome(nome);

        if (produtosOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        return produtosOptional.get();
    }

}
