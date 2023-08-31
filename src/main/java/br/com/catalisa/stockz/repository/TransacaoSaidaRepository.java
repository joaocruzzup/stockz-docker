package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.TransacaoSaida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoSaidaRepository extends JpaRepository<TransacaoSaida, Long> {

}
