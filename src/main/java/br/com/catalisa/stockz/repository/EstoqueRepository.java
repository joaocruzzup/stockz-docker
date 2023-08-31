package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.Estoque;
import br.com.catalisa.stockz.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByProduto(Produto produto);

}
