package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Fornecedor;
import br.com.catalisa.stockz.model.dto.FornecedoresDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FornecedoresMapper {
    public FornecedoresDTO toFornecedoresDTO(Fornecedor fornecedor){
        FornecedoresDTO fornecedoresDTO = new FornecedoresDTO();
        BeanUtils.copyProperties(fornecedor, fornecedoresDTO);
        return fornecedoresDTO;
    }
    public Fornecedor toFornecedores(FornecedoresDTO fornecedoresDTO){
        Fornecedor fornecedor = new Fornecedor();
        BeanUtils.copyProperties(fornecedoresDTO, fornecedor);
        return fornecedor;
    }
}
