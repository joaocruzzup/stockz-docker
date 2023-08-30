package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.TransacaoEntrada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoEntradaRepository extends JpaRepository<TransacaoEntrada, Long> {
}
