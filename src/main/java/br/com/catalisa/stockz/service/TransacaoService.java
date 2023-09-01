package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.Transacao;
import br.com.catalisa.stockz.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TransacaoService {
    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> buscarTransacoesPorProduto(Produto produto) {
        return transacaoRepository.findByProduto(produto);
    }
}
