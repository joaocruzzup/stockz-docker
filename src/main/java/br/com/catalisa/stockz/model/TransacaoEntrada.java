    package br.com.catalisa.stockz.model;

    import lombok.Getter;
    import lombok.Setter;

    import javax.persistence.*;

    @Entity
    @Getter
    @Setter
    public class TransacaoEntrada extends Transacao {

        @ManyToOne
        @JoinColumn(name = "fornecedor_id")
        private Fornecedor fornecedor;

    }
