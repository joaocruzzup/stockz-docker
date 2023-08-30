package br.com.catalisa.stockz.model.dto;

import br.com.catalisa.stockz.model.Compradores;
import br.com.catalisa.stockz.model.Fornecedores;
import br.com.catalisa.stockz.model.Produtos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoSaidaDTO {
    private Integer quantidade;

    private Produtos produto;

    private Compradores comprador;

    @JsonIgnore
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora = LocalDateTime.now();
}
