package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    @Valid
    private Usuario usuario;
    @ManyToOne
    @NotNull
    @Valid
    private Produto produto;
    @Positive
    private int quantidade;
    @Enumerated
    @NotNull
    private GatewayPagamento gateway;

    public Compra(Usuario usuario, Produto produto, int quantidade, GatewayPagamento gateway) {
        this.usuario = usuario;
        this.produto = produto;
        this.quantidade = quantidade;
        this.gateway = gateway;
    }

    @Deprecated
    public Compra() {
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                '}';
    }

    public Long getId() {
        return id;
    }
}
