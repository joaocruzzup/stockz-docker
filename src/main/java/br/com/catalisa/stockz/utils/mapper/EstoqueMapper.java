package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Estoque;
import br.com.catalisa.stockz.model.dto.EstoqueDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EstoqueMapper {
    public EstoqueDTO toEstoqueDTO(Estoque estoque){
        EstoqueDTO estoqueDTO = new EstoqueDTO();
        BeanUtils.copyProperties(estoque, estoqueDTO);
        return estoqueDTO;
    }
    public Estoque toEstoque(EstoqueDTO estoqueDTO){
        Estoque estoque = new Estoque();
        BeanUtils.copyProperties(estoqueDTO, estoque);
        return estoque;
    }
}
