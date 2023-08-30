package br.com.catalisa.stockz.model;

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
public class Produtos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    @PositiveOrZero
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categorias categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<TransacaoEntrada> transacoesEntrada;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<TransacaoSaida> transacoesSaida;

//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(
//            name = "produto_fornecedor",
//            joinColumns = @JoinColumn(name = "produto_id"),
//            inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
//    )
//    private List<Fornecedores> fornecedores;
//
//    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
//    private List<Transacoes> transacoes;
}
