package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Fornecedor;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.TransacaoEntrada;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaResponseDTO;
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

    @Autowired
    private ProdutosMapper produtosMapper;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    public TransacaoEntradaDTO toTransacaoEntradaDTO(TransacaoEntrada transacaoEntrada){
        TransacaoEntradaDTO transacaoEntradaDTO = new TransacaoEntradaDTO();
        BeanUtils.copyProperties(transacaoEntrada, transacaoEntradaDTO);
        return transacaoEntradaDTO;
    }

    public TransacaoEntrada toTransacao(TransacaoEntradaDTO transacaoEntradaDTO){

        TransacaoEntrada transacaoEntrada = new TransacaoEntrada();
        transacaoEntrada.setDataHora(transacaoEntradaDTO.getDataHora());
        transacaoEntrada.setQuantidade(transacaoEntradaDTO.getQuantidade());

        if (transacaoEntradaDTO.getProduto() != null && transacaoEntradaDTO.getProduto().getId() != null) {
            Optional<Produto> produtoOptional = produtoRepository.findById(transacaoEntradaDTO.getProduto().getId());
            Produto produto = produtoOptional.orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado."));
            transacaoEntrada.setProduto(produto);
        } else {
            Produto novoProduto = new Produto();
            novoProduto.setNome(transacaoEntradaDTO.getProduto().getNome());
            novoProduto.setDescricao(transacaoEntradaDTO.getProduto().getDescricao());
            novoProduto.setPreco(transacaoEntradaDTO.getProduto().getPreco());
            novoProduto.setStatusProduto(StatusProduto.ATIVO);


            Produto produtoSalvo = produtoRepository.save(novoProduto);
            transacaoEntrada.setProduto(produtoSalvo);
        }

        // Associar o fornecedor à Transacao
        Fornecedor fornecedor = fornecedorRepository.findByEmail(transacaoEntradaDTO.getEmailFornecedor())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Fornecedor não encontrado."));
        transacaoEntrada.setUsuario(fornecedor);
        transacaoEntrada.setFornecedor(fornecedor);

        transacaoEntrada.setTipoTransacao(TipoTransacao.ENTRADA);

        return transacaoEntrada;
    }

    public TransacaoEntradaResponseDTO toTransacaoEntradaResponseDTO(TransacaoEntrada transacaoEntrada){
        TransacaoEntradaResponseDTO transacaoResponse = new TransacaoEntradaResponseDTO();
        transacaoResponse.setDataHoraEntrada(transacaoEntrada.getDataHora());
        transacaoResponse.setQuantidade(transacaoEntrada.getQuantidade());
        transacaoResponse.setProduto(produtosMapper.toProdutoResponse(produtosMapper.toProdutosDTO(transacaoEntrada.getProduto())));
        transacaoResponse.setFornecedor(fornecedorMapper.toFornecedoresDTO(transacaoEntrada.getFornecedor()));
        return transacaoResponse;
    }
}
