package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriasRepository extends JpaRepository<Categoria, Long> {
}
