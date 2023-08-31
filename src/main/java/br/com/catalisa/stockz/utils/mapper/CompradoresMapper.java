package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.dto.CompradoresDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompradoresMapper {
    public CompradoresDTO toCompradoresDto(Comprador comprador){
        CompradoresDTO compradoresDTO = new CompradoresDTO();
        BeanUtils.copyProperties(comprador, compradoresDTO);
        return compradoresDTO;
    }
    public Comprador toCompradores(CompradoresDTO compradoresDTO){
        Comprador comprador = new Comprador();
        BeanUtils.copyProperties(compradoresDTO, comprador);
        return comprador;
    }
}
