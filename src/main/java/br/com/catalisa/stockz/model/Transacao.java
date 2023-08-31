package br.com.catalisa.stockz.model;

import br.com.catalisa.stockz.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transacoes_estoque")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Estoque estoque;

}
