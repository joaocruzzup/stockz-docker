package br.com.catalisa.stockz.model.dto;

import br.com.catalisa.stockz.model.Produto;
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
public class TransacaoEntradaDTO {

    private Integer quantidade;

    private Produto produto;

    private String emailFornecedor;

    @JsonIgnore
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora = LocalDateTime.now();
}
