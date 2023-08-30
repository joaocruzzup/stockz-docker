package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.exception.AtributoNaoPreenchidoException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categorias;
import br.com.catalisa.stockz.model.Produtos;
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

        List<Produtos> produtosList = produtosRepository.findAll();
        List<ProdutosDTO> produtosDTOList = new ArrayList<>();
        for (Produtos produtos: produtosList) {
            if (produtos.getStatusProduto() == StatusProduto.ATIVO){
                ProdutosDTO produtosDTO = produtosMapper.toProdutosDTO(produtos);
                produtosDTOList.add(produtosDTO);
            }
        }

        return produtosDTOList;
    }

    public ProdutosDTO listarPorId(Long id) throws Exception {

        Optional<Produtos> produtosOptional = produtosRepository.findById(id);

        if (produtosOptional.isEmpty() || produtosOptional.get().getStatusProduto() == StatusProduto.INATIVO){
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        Produtos produtos = produtosOptional.get();
        ProdutosDTO produtosDTO = produtosMapper.toProdutosDTO(produtos);

        return produtosDTO;
    }

    //ToDo tentar mudar a categoria
    public ProdutosDTO criar(ProdutosDTO produtosDTO) throws Exception {
        Optional<Categorias> categoriaOptional = categoriasRepository.findById(produtosDTO.getCategoria().getId());
        if (categoriaOptional.isEmpty()){
            throw new AtributoNaoPreenchidoException("Atributo categoria preenchido incorretamente");
        }
        produtosDTO.setQuantidade(0);
        Produtos produtos = produtosMapper.toProdutos(produtosDTO);
        produtosRepository.save(produtos);
        produtosDTO.setCategoria(categoriaOptional.get());
        return produtosDTO;
    }

    public ProdutosDTO atualizar(Long id, ProdutosDTO produtosDTO) throws Exception {

        Optional<Produtos> produtosOptional = produtosRepository.findById(id);
        if (produtosOptional.isEmpty()){
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        Produtos produtoEncontrado = produtosOptional.get();

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
            Optional<Categorias> categoriaOptional = categoriasRepository.findById(produtosDTO.getCategoria().getId());
            if (categoriaOptional.isEmpty()){
                throw new EntidadeNaoEncontradaException("Categoria não encontrada");
            }
            produtoEncontrado.setCategoria(categoriaOptional.get());
        }
        produtosRepository.save(produtoEncontrado);

        return produtosMapper.toProdutosDTO(produtoEncontrado);
    }

    public void deletar(Long id) throws Exception {
        Optional<Produtos> produtosOptional = produtosRepository.findById(id);
        if (produtosOptional.isEmpty()){
            throw new EntidadeNaoEncontradaException("Produto não encontrado");
        }
        Produtos produtos = produtosOptional.get();
        produtos.setStatusProduto(StatusProduto.INATIVO);
        produtosRepository.delete(produtos);
    }
}
