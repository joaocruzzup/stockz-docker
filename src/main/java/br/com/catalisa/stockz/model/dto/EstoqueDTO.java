package br.com.catalisa.stockz.model.dto;

import br.com.catalisa.stockz.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueDTO {

    @NotNull(message = "Produto não pode ser nulo")
    private Produto produto;

    @Positive(message = "A quantidade deve ser maior que 0")
    @NotNull(message = "A Quantidade não pode ser nula.")
    private Integer quantidade;

    @NotEmpty(message = "A Data não pode ser vazia")
    private LocalDateTime dataHoraRegistro;

}
