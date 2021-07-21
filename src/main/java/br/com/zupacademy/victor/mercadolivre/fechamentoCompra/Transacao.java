package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private StatusTransacao status;
    @NotBlank
    private String idTransacaoGateway;
    @ManyToOne
    private Compra compra;
    @NotNull
    private LocalDateTime instante;

    public Transacao(StatusTransacao status, String idTransacaoGateway, Compra compra) {

        this.status = status;
        this.idTransacaoGateway = idTransacaoGateway;
        this.compra = compra;
        this.instante = LocalDateTime.now();
    }

    @Deprecated
    public Transacao() {

    }

    public boolean concluidaComSucesso(){
        return this.status.equals(StatusTransacao.sucesso);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(idTransacaoGateway, transacao.idTransacaoGateway);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransacaoGateway);
    }

}
