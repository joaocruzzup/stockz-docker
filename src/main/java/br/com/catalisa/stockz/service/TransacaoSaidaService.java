package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.Estoque;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.TransacaoSaida;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import br.com.catalisa.stockz.repository.CompradorRepository;
import br.com.catalisa.stockz.repository.EstoqueRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import br.com.catalisa.stockz.repository.TransacaoSaidaRepository;
import br.com.catalisa.stockz.utils.mapper.TransacaoSaidaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoSaidaService {
    @Autowired
    private TransacaoSaidaRepository transacaoSaidaRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TransacaoSaidaMapper transacaoSaidaMapper;

    public List<TransacaoSaidaDTO> listarTodos(){

        List<TransacaoSaida> transacaoSaidaList = transacaoSaidaRepository.findAll();
        List<TransacaoSaidaDTO> transacaoSaidaDTOList = new ArrayList<>();
        for (TransacaoSaida transacaoSaida: transacaoSaidaList) {
            TransacaoSaidaDTO transacaoSaidaDTO = transacaoSaidaMapper.toTransacaoSaidaDTO(transacaoSaida);
            transacaoSaidaDTOList.add(transacaoSaidaDTO);
        }

        return transacaoSaidaDTOList;
    }

    public TransacaoSaidaDTO listarPorId(Long id) throws Exception {
        TransacaoSaida transacaoSaida = buscarTransacaoSaidaPorId(id);
        TransacaoSaidaDTO transacaoSaidaDTO = transacaoSaidaMapper.toTransacaoSaidaDTO(transacaoSaida);

        return transacaoSaidaDTO;
    }

    public TransacaoSaidaDTO criar(TransacaoSaidaDTO transacaoSaidaDTO) throws Exception {

        Comprador comprador = buscarComprador(transacaoSaidaDTO.getEmailComprador());
        Produto produto = buscarProduto(transacaoSaidaDTO.getProduto().getId());
        TransacaoSaida transacaoSaida = criarTransacaoSaida(transacaoSaidaDTO, produto);
        Estoque estoqueEncontrado = buscarEstoqueDoProduto(produto);

        atualizarEstoque(estoqueEncontrado, transacaoSaida);

        return transacaoSaidaDTO;
    }

    public TransacaoSaidaDTO atualizar(Long id, TransacaoSaidaDTO transacaoSaidaDTO) throws Exception {
        TransacaoSaida transacaoSaida = buscarTransacaoSaidaPorId(id);
        TransacaoSaidaDTO fornecedoresDTORetorno = transacaoSaidaMapper.toTransacaoSaidaDTO(transacaoSaida);

        if (transacaoSaidaDTO.getProduto() != null){
            fornecedoresDTORetorno.setProduto(transacaoSaidaDTO.getProduto());
        }
        if (transacaoSaidaDTO.getQuantidade() != null){
            fornecedoresDTORetorno.setQuantidade(transacaoSaidaDTO.getQuantidade());
        }
        if (transacaoSaidaDTO.getEmailComprador() != null){
            fornecedoresDTORetorno.setEmailComprador(transacaoSaidaDTO.getEmailComprador());
        }

        return fornecedoresDTORetorno;
    }

    public void deletar(Long id) throws Exception {
        TransacaoSaida transacaoSaida = buscarTransacaoSaidaPorId(id);
        transacaoSaidaRepository.delete(transacaoSaida);
    }

    private TransacaoSaida buscarTransacaoSaidaPorId(Long id) throws Exception {
        Optional<TransacaoSaida> transacaoSaidaOptional = transacaoSaidaRepository.findById(id);

        if (transacaoSaidaOptional.isEmpty()) {
            throw new Exception("Transação de saída não encontrada");
        }
        return transacaoSaidaOptional.get();
    }

    private Comprador buscarComprador(String email) throws EntidadeNaoEncontradaException {
        return compradorRepository.findByEmail(email)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Comprador não encontrado"));
    }

    private Produto buscarProduto(Long produtoId) throws Exception {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado"));
    }

    private TransacaoSaida criarTransacaoSaida(TransacaoSaidaDTO transacaoSaidaDTO, Produto produto) throws Exception {
        TransacaoSaida transacaoSaida = transacaoSaidaMapper.toTransacaoSaida(transacaoSaidaDTO);
        transacaoSaida.setEstoque(buscarEstoqueDoProduto(produto));
        return transacaoSaida;
    }

    private Estoque buscarEstoqueDoProduto(Produto produto) throws Exception {
        return estoqueRepository.findByProduto(produto)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Estoque não presente"));
    }

    private void atualizarEstoque(Estoque estoque, TransacaoSaida transacaoSaida) {
        estoque.setQuantidade(estoque.getQuantidade() - transacaoSaida.getQuantidade());
        estoque.getTransacoes().add(transacaoSaida);

        produtoRepository.save(estoque.getProduto());
        estoqueRepository.save(estoque);
        transacaoSaidaRepository.save(transacaoSaida);
    }
}
