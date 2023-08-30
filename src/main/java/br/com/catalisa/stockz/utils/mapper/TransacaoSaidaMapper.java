package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.TransacaoEntrada;
import br.com.catalisa.stockz.model.TransacaoSaida;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TransacaoSaidaMapper {
    public TransacaoSaidaDTO toTransacaoSaidaDTO(TransacaoSaida transacaoSaida){
        TransacaoSaidaDTO transacaoSaidaDTO = new TransacaoSaidaDTO();
        BeanUtils.copyProperties(transacaoSaida, transacaoSaidaDTO);
        return transacaoSaidaDTO;
    }
    public TransacaoSaida toTransacaoSaida(TransacaoSaidaDTO transacaoSaidaDTO){
        TransacaoSaida transacaoSaida = new TransacaoSaida();
        BeanUtils.copyProperties(transacaoSaidaDTO, transacaoSaida);
        return transacaoSaida;
    }
}
