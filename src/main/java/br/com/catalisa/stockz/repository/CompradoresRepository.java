package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.Compradores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompradoresRepository extends JpaRepository<Compradores, Long> {
}
