package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Produtos;
import br.com.catalisa.stockz.model.dto.ProdutosDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProdutosMapper {
    public ProdutosDTO toProdutosDTO(Produtos produtos){
        ProdutosDTO categoriasDTO = new ProdutosDTO();
        BeanUtils.copyProperties(produtos, categoriasDTO);
        return categoriasDTO;
    }
    public Produtos toProdutos(ProdutosDTO produtosDTO){
        Produtos produtos = new Produtos();
        BeanUtils.copyProperties(produtosDTO, produtos);
        return produtos;
    }
}
