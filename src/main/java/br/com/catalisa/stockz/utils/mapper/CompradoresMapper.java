package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Compradores;
import br.com.catalisa.stockz.model.dto.CompradoresDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompradoresMapper {
    public CompradoresDTO toCompradoresDto(Compradores compradores){
        CompradoresDTO compradoresDTO = new CompradoresDTO();
        BeanUtils.copyProperties(compradores, compradoresDTO);
        return compradoresDTO;
    }
    public Compradores toCompradores(CompradoresDTO compradoresDTO){
        Compradores compradores = new Compradores();
        BeanUtils.copyProperties(compradoresDTO, compradores);
        return compradores;
    }
}
