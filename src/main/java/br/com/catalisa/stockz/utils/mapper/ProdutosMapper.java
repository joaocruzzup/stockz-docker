package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
import br.com.catalisa.stockz.model.dto.ProdutoDTOResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutosMapper {

    @Autowired
    private CategoriaMapper categoriaMapper;
    public ProdutoDTO toProdutosDTO(Produto produto){
        ProdutoDTO categoriasDTO = new ProdutoDTO();
        BeanUtils.copyProperties(produto, categoriasDTO);
        return categoriasDTO;
    }
    public Produto toProdutos(ProdutoDTO produtoDTO){
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDTO, produto);
        return produto;
    }

    public ProdutoDTOResponse toProdutoResponse(ProdutoDTO produtoDTO){
        ProdutoDTOResponse produtoDTOResponse = new ProdutoDTOResponse();
        produtoDTOResponse.setNome(produtoDTO.getNome());
        produtoDTOResponse.setDescricao(produtoDTO.getDescricao());
        produtoDTOResponse.setPreco(produtoDTO.getPreco());
        produtoDTOResponse.setCategoria(categoriaMapper.toCategoriasDto(produtoDTO.getCategoria()));
        return produtoDTOResponse;
    }
}
