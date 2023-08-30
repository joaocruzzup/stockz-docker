package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.model.Categorias;
import br.com.catalisa.stockz.model.Produtos;
import br.com.catalisa.stockz.model.TransacaoEntrada;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.repository.ProdutosRepository;
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
    private ProdutosRepository produtosRepository;

    @Autowired
    private TransacaoEntradaMapper transacaoEntradaMapper;

    public List<TransacaoEntradaDTO> listarTodos(){

        List<TransacaoEntrada> transacaoEntradaList = transacaoEntradaRepository.findAll();
        List<TransacaoEntradaDTO> transacaoEntradaDTOList = new ArrayList<>();
        for (TransacaoEntrada transacaoEntrada: transacaoEntradaList) {
            TransacaoEntradaDTO transacaoEntradaDTO = transacaoEntradaMapper.toTransacaoEntradaDTO(transacaoEntrada);
            transacaoEntradaDTOList.add(transacaoEntradaDTO);
        }

        return transacaoEntradaDTOList;
    }

    public TransacaoEntradaDTO listarPorId(Long id) throws Exception {

        Optional<TransacaoEntrada> transacaoEntradaOptional = transacaoEntradaRepository.findById(id);

        if (transacaoEntradaOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        TransacaoEntrada transacaoEntrada = transacaoEntradaOptional.get();
        TransacaoEntradaDTO transacaoEntradaDTO = transacaoEntradaMapper.toTransacaoEntradaDTO(transacaoEntrada);

        return transacaoEntradaDTO;
    }

    public TransacaoEntradaDTO criar(TransacaoEntradaDTO transacaoEntradaDTO) throws Exception {
        Optional<Produtos> produtosOptional = produtosRepository.findById(transacaoEntradaDTO.getProduto().getId());
        if (produtosOptional.isEmpty()){
            throw new Exception("Categoria não presente");
        }
        TransacaoEntrada transacaoEntrada = transacaoEntradaMapper.toTransacao(transacaoEntradaDTO);
        // salvando a nova quantidade do produto
        Produtos produtos = produtosOptional.get();
        produtos.setQuantidade(produtos.getQuantidade() + transacaoEntradaDTO.getQuantidade());
        produtosRepository.save(produtos);

        transacaoEntradaRepository.save(transacaoEntrada);
        return transacaoEntradaDTO;
    }

    public TransacaoEntradaDTO atualizar(Long id, TransacaoEntradaDTO transacaoEntradaDTO) throws Exception {

        Optional<TransacaoEntrada> transacaoEntradaOptional = transacaoEntradaRepository.findById(id);
        if (transacaoEntradaOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        TransacaoEntrada transacaoEntrada = transacaoEntradaOptional.get();
        TransacaoEntradaDTO fornecedoresDTORetorno = transacaoEntradaMapper.toTransacaoEntradaDTO(transacaoEntrada);

        if (transacaoEntradaDTO.getProduto() != null){
            fornecedoresDTORetorno.setProduto(transacaoEntradaDTO.getProduto());
        }
        if (transacaoEntradaDTO.getQuantidade() != null){
            fornecedoresDTORetorno.setQuantidade(transacaoEntradaDTO.getQuantidade());
        }
        if (transacaoEntradaDTO.getFornecedor() != null){
            fornecedoresDTORetorno.setFornecedor(transacaoEntradaDTO.getFornecedor());
        }

        return fornecedoresDTORetorno;
    }

    public void deletar(Long id) throws Exception {
        Optional<TransacaoEntrada> transacaoEntradaOptional = transacaoEntradaRepository.findById(id);
        if (transacaoEntradaOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        TransacaoEntrada transacaoEntrada = transacaoEntradaOptional.get();
        transacaoEntradaRepository.delete(transacaoEntrada);
    }
}
