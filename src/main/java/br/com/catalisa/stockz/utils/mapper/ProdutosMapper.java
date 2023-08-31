package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.ProdutosDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProdutosMapper {
    public ProdutosDTO toProdutosDTO(Produto produto){
        ProdutosDTO categoriasDTO = new ProdutosDTO();
        BeanUtils.copyProperties(produto, categoriasDTO);
        return categoriasDTO;
    }
    public Produto toProdutos(ProdutosDTO produtosDTO){
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtosDTO, produto);
        return produto;
    }
}
