package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.dto.CompradorDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompradorMapper {
    public CompradorDTO toCompradoresDto(Comprador comprador){
        CompradorDTO compradorDTO = new CompradorDTO();
        BeanUtils.copyProperties(comprador, compradorDTO);
        return compradorDTO;
    }
    public Comprador toCompradores(CompradorDTO compradorDTO){
        Comprador comprador = new Comprador();
        BeanUtils.copyProperties(compradorDTO, comprador);
        return comprador;
    }
}
