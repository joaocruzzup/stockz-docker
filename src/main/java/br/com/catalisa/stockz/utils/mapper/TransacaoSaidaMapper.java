package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.TransacaoSaida;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import br.com.catalisa.stockz.repository.CompradoresRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransacaoSaidaMapper {

    @Autowired
    private CompradoresRepository compradoresRepository;
    public TransacaoSaidaDTO toTransacaoSaidaDTO(TransacaoSaida transacaoSaida){
        TransacaoSaidaDTO transacaoSaidaDTO = new TransacaoSaidaDTO();
        BeanUtils.copyProperties(transacaoSaida, transacaoSaidaDTO);
        return transacaoSaidaDTO;
    }
    public TransacaoSaida toTransacaoSaida(TransacaoSaidaDTO transacaoSaidaDTO){
        TransacaoSaida transacaoSaida = new TransacaoSaida();
        transacaoSaida.setDataHora(transacaoSaidaDTO.getDataHora());
        transacaoSaida.setQuantidade(transacaoSaidaDTO.getQuantidade());

        Comprador comprador = compradoresRepository.findByEmail(transacaoSaidaDTO.getEmailComprador()).get();
        transacaoSaida.setUsuario(comprador);
        transacaoSaida.setComprador(comprador);

        transacaoSaida.setProduto(transacaoSaidaDTO.getProduto());

        transacaoSaida.setTipoTransacao(TipoTransacao.SAIDA);
        return transacaoSaida;
    }
}
