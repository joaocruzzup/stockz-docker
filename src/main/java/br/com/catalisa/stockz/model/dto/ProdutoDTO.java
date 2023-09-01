package br.com.catalisa.stockz.model.dto;

import br.com.catalisa.stockz.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    @NotEmpty(message = "Nome da categoria não pode ser vazio.")
    private String nome;

    @NotEmpty(message = "Nome da Descrição não pode ser vazio.")
    private String descricao;

    @NotNull(message = "Categoria não pode ser nula")
    private Categoria categoria;

    @NotNull(message = "O preço não pode ser nulo")
    @Positive(message = "Preço deve ser acima de zero.")
    private BigDecimal preco;

}
