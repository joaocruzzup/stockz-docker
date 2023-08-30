package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.Fornecedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedoresRepository extends JpaRepository<Fornecedores, Long> {
}
