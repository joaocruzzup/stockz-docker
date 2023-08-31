package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProdutosMapper {
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
}
