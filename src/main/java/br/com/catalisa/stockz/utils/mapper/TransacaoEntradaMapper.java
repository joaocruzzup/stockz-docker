package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.model.Fornecedor;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.TransacaoEntrada;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.repository.FornecedorRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransacaoEntradaMapper {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public TransacaoEntradaDTO toTransacaoEntradaDTO(TransacaoEntrada transacaoEntrada){
        TransacaoEntradaDTO transacaoEntradaDTO = new TransacaoEntradaDTO();
        BeanUtils.copyProperties(transacaoEntrada, transacaoEntradaDTO);
        return transacaoEntradaDTO;
    }
    public TransacaoEntrada toTransacao(TransacaoEntradaDTO transacaoEntradaDTO){

        TransacaoEntrada transacaoEntrada = new TransacaoEntrada();
        transacaoEntrada.setDataHora(transacaoEntradaDTO.getDataHora());
        transacaoEntrada.setQuantidade(transacaoEntradaDTO.getQuantidade());

        Fornecedor fornecedor = fornecedorRepository.findByEmail(transacaoEntradaDTO.getEmailFornecedor()).get();
        transacaoEntrada.setUsuario(fornecedor);
        transacaoEntrada.setFornecedor(fornecedor);

        Optional<Produto> produtoOptional = produtoRepository.findById(transacaoEntradaDTO.getProduto().getId());
        Produto produto = produtoOptional.get();

        transacaoEntrada.setProduto(produto);

        transacaoEntrada.setTipoTransacao(TipoTransacao.ENTRADA);

        return transacaoEntrada;
    }
}
