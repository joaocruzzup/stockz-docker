package br.com.catalisa.stockz.service;

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
            ProdutosDTO produtosDTO = produtosMapper.toProdutosDTO(produtos);
            produtosDTOList.add(produtosDTO);
        }

        return produtosDTOList;
    }

    public ProdutosDTO listarPorId(Long id) throws Exception {

        Optional<Produtos> produtosOptional = produtosRepository.findById(id);

        if (produtosOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        Produtos produtos = produtosOptional.get();
        ProdutosDTO produtosDTO = produtosMapper.toProdutosDTO(produtos);

        return produtosDTO;
    }

    //ToDo tentar mudar a categoria
    public ProdutosDTO criar(ProdutosDTO produtosDTO) throws Exception {
        Optional<Categorias> categoriaOptional = categoriasRepository.findById(produtosDTO.getCategoria().getId());
        if (categoriaOptional.isEmpty()){
            throw new Exception("Categoria não presente");
        }
        produtosDTO.setQuantidade(0);
        Produtos produtos = produtosMapper.toProdutos(produtosDTO);
        produtosRepository.save(produtos);
        produtosDTO.setCategoria(categoriaOptional.get());
        return produtosDTO;
    }

    //ToDo revisar esse método na parte da categoria
    public ProdutosDTO atualizar(Long id, ProdutosDTO produtosDTO) throws Exception {

        Optional<Produtos> produtosOptional = produtosRepository.findById(id);
        if (produtosOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        Produtos produtos = produtosOptional.get();
        ProdutosDTO produtosDTORetorno = produtosMapper.toProdutosDTO(produtos);

        if (produtosDTO.getNome() != null){
            produtosDTORetorno.setNome(produtosDTO.getNome());
        }
        if (produtosDTO.getPreco() != null){
            produtosDTORetorno.setPreco(produtosDTO.getPreco());
        }
        if (produtosDTO.getDescricao() != null){
            produtosDTORetorno.setDescricao(produtosDTO.getDescricao());
        }
        if (produtosDTO.getCategoria().getId() != null){
            produtosDTORetorno.setCategoria(produtosDTO.getCategoria());
        }

        return produtosDTORetorno;
    }

    public void deletar(Long id) throws Exception {
        Optional<Produtos> produtosOptional = produtosRepository.findById(id);
        if (produtosOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        Produtos produtos = produtosOptional.get();
        produtosRepository.delete(produtos);
    }
}
