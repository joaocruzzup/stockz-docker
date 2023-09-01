package br.com.catalisa.stockz.model.dto;

import br.com.catalisa.stockz.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoEntradaDTO {

    @NotNull(message = "Quantidade não pode ser nula.")
    @Positive(message = "Quantidade deve ser positiva.")
    private Integer quantidade;

    @NotNull(message = "Produto não pode ser nulo.")
    private Produto produto;

    @Email(message = "Digite um email válido. Exemplo: seunome@example.com")
    @NotEmpty(message = "Email não pode estar vazio.")
    private String emailFornecedor;

    @JsonIgnore
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora = LocalDateTime.now();
}
