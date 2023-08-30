package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.TransacaoEntrada;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TransacaoEntradaMapper {
    public TransacaoEntradaDTO toTransacaoEntradaDTO(TransacaoEntrada transacaoEntrada){
        TransacaoEntradaDTO transacaoEntradaDTO = new TransacaoEntradaDTO();
        BeanUtils.copyProperties(transacaoEntrada, transacaoEntradaDTO);
        return transacaoEntradaDTO;
    }
    public TransacaoEntrada toTransacao(TransacaoEntradaDTO transacaoEntradaDTO){
        TransacaoEntrada transacaoEntrada = new TransacaoEntrada();
        BeanUtils.copyProperties(transacaoEntradaDTO, transacaoEntrada);
        return transacaoEntrada;
    }
}
