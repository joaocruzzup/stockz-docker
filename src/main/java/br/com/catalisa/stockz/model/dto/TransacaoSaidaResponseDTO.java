package br.com.catalisa.stockz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoSaidaResponseDTO {
    private Integer quantidade;

    private ProdutoDTOResponse produto;

    private CompradorDTO comprador;

    private LocalDateTime dataHoraSaida;
}
