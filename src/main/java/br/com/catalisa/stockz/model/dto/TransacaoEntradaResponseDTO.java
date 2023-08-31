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
public class TransacaoEntradaResponseDTO {
    private Integer quantidade;

    private ProdutoDTOResponse produto;

    private FornecedorDTO fornecedor;

    private LocalDateTime dataHoraEntrada;
}
