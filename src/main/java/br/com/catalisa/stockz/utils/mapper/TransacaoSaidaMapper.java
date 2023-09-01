package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.TransacaoEntrada;
import br.com.catalisa.stockz.model.TransacaoSaida;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaResponseDTO;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaResponseDTO;
import br.com.catalisa.stockz.repository.CompradorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransacaoSaidaMapper {

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private ProdutosMapper produtosMapper;

    @Autowired
    private CompradorMapper compradorMapper;

    public TransacaoSaidaDTO toTransacaoSaidaDTO(TransacaoSaida transacaoSaida){
        TransacaoSaidaDTO transacaoSaidaDTO = new TransacaoSaidaDTO();
        BeanUtils.copyProperties(transacaoSaida, transacaoSaidaDTO);
        return transacaoSaidaDTO;
    }
    public TransacaoSaida toTransacaoSaida(TransacaoSaidaDTO transacaoSaidaDTO){
        TransacaoSaida transacaoSaida = new TransacaoSaida();
        transacaoSaida.setQuantidade(transacaoSaidaDTO.getQuantidade());
        transacaoSaida.setDataHora(transacaoSaidaDTO.getDataHora());

        Comprador comprador = compradorRepository.findByEmail(transacaoSaidaDTO.getEmailComprador())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Comprador não encontrado."));

        transacaoSaida.setComprador(comprador);
        transacaoSaida.setUsuario(comprador);

        transacaoSaida.setProduto(transacaoSaidaDTO.getProduto());

        transacaoSaida.setTipoTransacao(TipoTransacao.SAIDA);
        return transacaoSaida;
    }

    public TransacaoSaidaResponseDTO toTransacaoSaidaResponseDTO(TransacaoSaida transacaoSaida){
        TransacaoSaidaResponseDTO transacaoResponse = new TransacaoSaidaResponseDTO();
        transacaoResponse.setDataHoraSaida(transacaoSaida.getDataHora());
        transacaoResponse.setQuantidade(transacaoSaida.getQuantidade());
        transacaoResponse.setProduto(produtosMapper.toProdutoResponse(produtosMapper.toProdutosDTO(transacaoSaida.getProduto())));
        transacaoResponse.setComprador(compradorMapper.toCompradoresDto(transacaoSaida.getComprador()));
        return transacaoResponse;
    }
}
