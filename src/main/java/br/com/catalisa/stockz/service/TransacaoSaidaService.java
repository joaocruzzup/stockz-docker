package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.model.Produtos;
import br.com.catalisa.stockz.model.TransacaoSaida;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import br.com.catalisa.stockz.repository.ProdutosRepository;
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
    private ProdutosRepository produtosRepository;

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

        Optional<TransacaoSaida> transacaoSaidaOptional = transacaoSaidaRepository.findById(id);

        if (transacaoSaidaOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        TransacaoSaida transacaoSaida = transacaoSaidaOptional.get();
        TransacaoSaidaDTO transacaoSaidaDTO = transacaoSaidaMapper.toTransacaoSaidaDTO(transacaoSaida);

        return transacaoSaidaDTO;
    }

    public TransacaoSaidaDTO criar(TransacaoSaidaDTO transacaoSaidaDTO) throws Exception {
        Optional<Produtos> produtosOptional = produtosRepository.findById(transacaoSaidaDTO.getProduto().getId());
        if (produtosOptional.isEmpty()){
            throw new Exception("Categoria não presente");
        }
        TransacaoSaida transacaoSaida = transacaoSaidaMapper.toTransacaoSaida(transacaoSaidaDTO);
        // salvando a nova quantidade do produto
        Produtos produtos = produtosOptional.get();
        if ((produtos.getQuantidade() - transacaoSaidaDTO.getQuantidade()) < 0){
            throw new Exception("Quantidade de saída inválida");
        }
        produtos.setQuantidade(produtos.getQuantidade() - transacaoSaidaDTO.getQuantidade());
        produtosRepository.save(produtos);

        transacaoSaidaRepository.save(transacaoSaida);
        return transacaoSaidaDTO;
    }

    public TransacaoSaidaDTO atualizar(Long id, TransacaoSaidaDTO transacaoSaidaDTO) throws Exception {

        Optional<TransacaoSaida> transacaoSaidaOptional = transacaoSaidaRepository.findById(id);
        if (transacaoSaidaOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        TransacaoSaida transacaoSaida = transacaoSaidaOptional.get();
        TransacaoSaidaDTO fornecedoresDTORetorno = transacaoSaidaMapper.toTransacaoSaidaDTO(transacaoSaida);

        if (transacaoSaidaDTO.getProduto() != null){
            fornecedoresDTORetorno.setProduto(transacaoSaidaDTO.getProduto());
        }
        if (transacaoSaidaDTO.getQuantidade() != null){
            fornecedoresDTORetorno.setQuantidade(transacaoSaidaDTO.getQuantidade());
        }
        if (transacaoSaidaDTO.getComprador() != null){
            fornecedoresDTORetorno.setComprador(transacaoSaidaDTO.getComprador());
        }

        return fornecedoresDTORetorno;
    }

    public void deletar(Long id) throws Exception {
        Optional<TransacaoSaida> transacaoSaidaOptional = transacaoSaidaRepository.findById(id);
        if (transacaoSaidaOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        TransacaoSaida transacaoSaida = transacaoSaidaOptional.get();
        transacaoSaidaRepository.delete(transacaoSaida);
    }
}
