package br.com.catalisa.stockz.model.dto;

import br.com.catalisa.stockz.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
    private String nome;

    private String descricao;

    private Categoria categoria;

    private BigDecimal preco;

}
