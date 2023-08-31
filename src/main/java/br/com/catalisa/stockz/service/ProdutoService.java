package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.exception.AtributoNaoPreenchidoException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
import br.com.catalisa.stockz.repository.CategoriaRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import br.com.catalisa.stockz.utils.mapper.ProdutosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriasRepository;

    @Autowired
    private ProdutosMapper produtosMapper;

    public List<ProdutoDTO> listarTodos(){

        List<Produto> produtoList = produtoRepository.findAll();
        List<ProdutoDTO> produtoDTOList = new ArrayList<>();
        for (Produto produto : produtoList) {
            if (produto.getStatusProduto() == StatusProduto.ATIVO){
                ProdutoDTO produtoDTO = produtosMapper.toProdutosDTO(produto);
                produtoDTOList.add(produtoDTO);
            }
        }

        return produtoDTOList;
    }

    public ProdutoDTO listarPorId(Long id) {
        Produto produtoEncontrado = buscarProdutoPorId(id);
        ProdutoDTO produtoDTO = produtosMapper.toProdutosDTO(produtoEncontrado);
        return produtoDTO;
    }

    public ProdutoDTO listarPorNome(String nome){
        Produto produtoEncontrado = buscarProdutoPorNome(nome);
        ProdutoDTO produtoDTO = produtosMapper.toProdutosDTO(produtoEncontrado);
        return produtoDTO;
    }

    //ToDo tentar mudar a categoria
    public ProdutoDTO criar(ProdutoDTO produtoDTO) {
        Optional<Categoria> categoriaOptional = categoriasRepository.findById(produtoDTO.getCategoria().getId());
        if (categoriaOptional.isEmpty()){
            throw new AtributoNaoPreenchidoException("Atributo categoria preenchido incorretamente");
        }
        Produto produto = produtosMapper.toProdutos(produtoDTO);
        produtoRepository.save(produto);
        produtoDTO.setCategoria(categoriaOptional.get());
        return produtoDTO;
    }

    public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTO) {
        Produto produtoEncontrado = buscarProdutoPorId(id);

        if (produtoDTO.getNome() != null){
            produtoEncontrado.setNome(produtoDTO.getNome());
        }
        if (produtoDTO.getPreco() != null){
            produtoEncontrado.setPreco(produtoDTO.getPreco());
        }
        if (produtoDTO.getDescricao() != null){
            produtoEncontrado.setDescricao(produtoDTO.getDescricao());
        }
        if (produtoDTO.getCategoria().getId() != null){
            Optional<Categoria> categoriaOptional = categoriasRepository.findById(produtoDTO.getCategoria().getId());
            if (categoriaOptional.isEmpty()){
                throw new EntidadeNaoEncontradaException("Categoria não encontrada");
            }
            produtoEncontrado.setCategoria(categoriaOptional.get());
        }
        produtoRepository.save(produtoEncontrado);

        return produtosMapper.toProdutosDTO(produtoEncontrado);
    }

    public void deletar(Long id) {
        Optional<Produto> produtosOptional = produtoRepository.findById(id);
        if (produtosOptional.isEmpty()){
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        Produto produto = produtosOptional.get();
        produto.setStatusProduto(StatusProduto.INATIVO);
        produtoRepository.delete(produto);
    }

    private Produto buscarProdutoPorId(Long id) {
        Optional<Produto> produtosOptional = produtoRepository.findById(id);

        if (produtosOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        return produtosOptional.get();
    }

    private Produto buscarProdutoPorNome(String nome) {
        Optional<Produto> produtosOptional = produtoRepository.findByNome(nome);

        if (produtosOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        return produtosOptional.get();
    }

}
