package br.com.catalisa.sotckz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
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

    @Positive
    private Long quantidade;

    @ManyToMany
    @JoinTable(
            name = "produto_comprador",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "comprador_id")
    )
    private List<Compradores> compradores;

    @ManyToMany
    @JoinTable(
            name = "produto_fornecedor",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
    )
    private List<Fornecedores> fornecedores;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Transacoes> transacoes;
}
