package br.com.catalisa.stockz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TransacaoSaida extends Transacao {

    @ManyToOne
    private Comprador comprador;

}
