package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Fornecedores;
import br.com.catalisa.stockz.model.dto.FornecedoresDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FornecedoresMapper {
    public FornecedoresDTO toFornecedoresDTO(Fornecedores fornecedores){
        FornecedoresDTO fornecedoresDTO = new FornecedoresDTO();
        BeanUtils.copyProperties(fornecedores, fornecedoresDTO);
        return fornecedoresDTO;
    }
    public Fornecedores toFornecedores(FornecedoresDTO fornecedoresDTO){
        Fornecedores fornecedores = new Fornecedores();
        BeanUtils.copyProperties(fornecedoresDTO, fornecedores);
        return fornecedores;
    }
}
