package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.model.Estoque;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.Transacao;
import br.com.catalisa.stockz.model.dto.EstoqueDTO;
import br.com.catalisa.stockz.repository.EstoqueRepository;
import br.com.catalisa.stockz.utils.mapper.EstoqueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {
    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueMapper estoqueMapper;

    public List<EstoqueDTO> listarTodos() {

        List<Estoque> estoqueList = estoqueRepository.findAll();
        List<EstoqueDTO> estoqueDTOList = new ArrayList<>();
        for (Estoque estoque : estoqueList) {
            EstoqueDTO estoqueDTO = estoqueMapper.toEstoqueDTO(estoque);
            estoqueDTOList.add(estoqueDTO);
        }

        return estoqueDTOList;
    }

    public void adicionarEstoque(Transacao transacao) {
        if (transacao.getTipoTransacao() == TipoTransacao.ENTRADA) {
            Produto produto = transacao.getProduto();
            Integer quantidade = transacao.getQuantidade();

            // Verificar se o produto já existe no estoque
            Optional<Estoque> estoqueDoProdutoOptional = estoqueRepository.findByProduto(produto);

            if (estoqueDoProdutoOptional.isEmpty()) {
                // Se o produto ainda não existe no estoque, criar um novo registro
                Estoque novoEstoque = new Estoque();
                novoEstoque.setProduto(produto);
                novoEstoque.setQuantidade(quantidade);
                novoEstoque.setTransacoes(Collections.singletonList(transacao));
                transacao.setEstoque(novoEstoque);

                estoqueRepository.save(novoEstoque);
            } else {
                // Se o produto já existe no estoque, atualizar a quantidade e associar a transação
                Estoque estoqueDoProduto = estoqueDoProdutoOptional.get();
                transacao.setEstoque(estoqueDoProduto);
                estoqueDoProduto.setQuantidade(estoqueDoProduto.getQuantidade() + quantidade);
                estoqueDoProduto.getTransacoes().add(transacao);

                estoqueRepository.save(estoqueDoProduto);
            }
        }
    }


}
