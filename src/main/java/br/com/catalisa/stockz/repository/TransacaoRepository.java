package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByProduto(Produto produto);
}
