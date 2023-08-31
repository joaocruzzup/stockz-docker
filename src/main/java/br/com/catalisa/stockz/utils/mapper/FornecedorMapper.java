package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Fornecedor;
import br.com.catalisa.stockz.model.dto.FornecedorDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FornecedorMapper {
    public FornecedorDTO toFornecedoresDTO(Fornecedor fornecedor){
        FornecedorDTO fornecedorDTO = new FornecedorDTO();
        BeanUtils.copyProperties(fornecedor, fornecedorDTO);
        return fornecedorDTO;
    }
    public Fornecedor toFornecedores(FornecedorDTO fornecedorDTO){
        Fornecedor fornecedor = new Fornecedor();
        BeanUtils.copyProperties(fornecedorDTO, fornecedor);
        return fornecedor;
    }
}
