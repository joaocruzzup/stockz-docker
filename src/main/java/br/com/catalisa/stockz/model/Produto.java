package br.com.catalisa.stockz.model;

import br.com.catalisa.stockz.enums.StatusProduto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusProduto statusProduto = StatusProduto.ATIVO;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

}
