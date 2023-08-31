package br.com.catalisa.stockz.repository;

import br.com.catalisa.stockz.model.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Long> {
    Optional<Comprador> findByEmail(String email);

}
