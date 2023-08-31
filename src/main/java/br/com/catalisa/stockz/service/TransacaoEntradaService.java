package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.*;
import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaResponseDTO;
import br.com.catalisa.stockz.repository.FornecedorRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import br.com.catalisa.stockz.repository.TransacaoEntradaRepository;
import br.com.catalisa.stockz.utils.mapper.TransacaoEntradaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoEntradaService {
    @Autowired
    private TransacaoEntradaRepository transacaoEntradaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TransacaoEntradaMapper transacaoEntradaMapper;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<TransacaoEntradaResponseDTO> listarTodos(){

        List<TransacaoEntrada> transacaoEntradaList = transacaoEntradaRepository.findAll();
        List<TransacaoEntradaResponseDTO> transacaoEntradaDTOList = new ArrayList<>();
        for (TransacaoEntrada transacaoEntrada: transacaoEntradaList) {
            TransacaoEntradaResponseDTO transacaoEntradaDTO = transacaoEntradaMapper.toTransacaoEntradaResponseDTO(transacaoEntrada);
            transacaoEntradaDTOList.add(transacaoEntradaDTO);
        }
        return transacaoEntradaDTOList;
    }

    public TransacaoEntradaResponseDTO listarPorId(Long id) throws Exception {
        TransacaoEntrada transacaoEntrada = buscarTransacaoEntradaPorId(id);
        TransacaoEntradaResponseDTO transacaoEntradaDTO = transacaoEntradaMapper.toTransacaoEntradaResponseDTO(transacaoEntrada);
        return transacaoEntradaDTO;
    }

    public TransacaoEntradaResponseDTO criar(TransacaoEntradaDTO transacaoEntradaDTO) throws Exception {
        Fornecedor fornecedor = buscarFornecedor(transacaoEntradaDTO.getEmailFornecedor());
        Produto produto = buscarProduto(transacaoEntradaDTO.getProduto().getId());

        TransacaoEntrada transacaoEntrada = criarTransacaoEntrada(transacaoEntradaDTO, produto);

        estoqueService.adicionarEstoque(transacaoEntrada);

        produtoRepository.save(produto);
        transacaoEntradaRepository.save(transacaoEntrada);

        return transacaoEntradaMapper.toTransacaoEntradaResponseDTO(transacaoEntrada);
    }

    private TransacaoEntrada buscarTransacaoEntradaPorId(Long id) throws Exception {
        Optional<TransacaoEntrada> transacaoEntradaOptional = transacaoEntradaRepository.findById(id);
        if (transacaoEntradaOptional.isEmpty()) {
            throw new Exception("Transação de entrada não encontrada");
        }
        return transacaoEntradaOptional.get();
    }

    private Fornecedor buscarFornecedor(String email) throws EntidadeNaoEncontradaException {
        return fornecedorRepository.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Fornecedor não encontrado"));
    }

    private TransacaoEntrada criarTransacaoEntrada(TransacaoEntradaDTO transacaoEntradaDTO, Produto produto) {
        TransacaoEntrada transacaoEntrada = transacaoEntradaMapper.toTransacao(transacaoEntradaDTO);
        transacaoEntrada.setProduto(produto);
        return transacaoEntrada;
    }

    private Produto buscarProduto(Long produtoId) throws Exception {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new Exception("Produto não encontrado"));
    }

}
