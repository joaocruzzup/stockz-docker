package br.com.catalisa.stockz.model.dto;

import br.com.catalisa.stockz.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDTO {

    private Produto produto;

    @PositiveOrZero
    private Integer quantidade;

    private LocalDateTime dataHoraRegistro;

}
