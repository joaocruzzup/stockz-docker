package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.Categorias;
import br.com.catalisa.stockz.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
    Optional<Produtos> findByCategoria(Categorias categorias);
}
