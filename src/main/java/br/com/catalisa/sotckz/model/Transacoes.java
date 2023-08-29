package br.com.catalisa.sotckz.model;

import br.com.catalisa.sotckz.enums.TipoTransacao;
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
public class Transacoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produtos produto;
}
