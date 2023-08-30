package br.com.catalisa.stockz.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransacaoSaida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private Integer quantidade;

    //    @ManyToOne
//    @JoinColumn(name = "produto_id")
    @JoinTable(name = "produto_transacao_saida",
            joinColumns = @JoinColumn(name = "transacao_saida_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    @ManyToOne
    private Produtos produto;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Compradores comprador;
}
