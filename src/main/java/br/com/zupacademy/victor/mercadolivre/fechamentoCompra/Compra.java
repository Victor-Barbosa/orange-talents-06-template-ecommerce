package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "compra")
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
    private GatewayPagamento gatewayPagamento;
    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();

    public Compra(Usuario usuario, Produto produto, int quantidade, GatewayPagamento gatewayPagamento) {
        this.usuario = usuario;
        this.produto = produto;
        this.quantidade = quantidade;
        this.gatewayPagamento = gatewayPagamento;
    }
    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", gatewayPagamento=" + gatewayPagamento +
                '}';
    }

    @Deprecated
    public Compra() {
    }

    public String urlRedirecionamento(
            UriComponentsBuilder uriComponentsBuilder) {
        return this.gatewayPagamento.criaUrlRetorno(this, uriComponentsBuilder);
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public Usuario getDonoProduto() {
        return produto.getUsuario();
    }

    public void adcionaTransacao(@Valid RetornoGatewayPagamento request){
        Transacao novaTransacao = request.toTransacao(this);

        Assert.isTrue(!this.transacoes.contains(novaTransacao),
                "Já existe uma transação igual a essa processada" + novaTransacao);

        Assert.isTrue(transacoesConcluidasComSucesso().isEmpty(),"Essa compra já foi concluida com sucesso");

        this.transacoes.add(novaTransacao);
    }

    private Set<Transacao> transacoesConcluidasComSucesso() {
        Set<Transacao> transacoesConcluidasComSucesso = this.transacoes.stream()
                .filter(Transacao::concluidaComSucesso)
                .collect(Collectors.toSet());

        Assert.isTrue(transacoesConcluidasComSucesso.size() <= 1, "não era pra chegar aqui! Já existe uma compra concluida com sucesso para essa compra: " + this.id);

        return transacoesConcluidasComSucesso;
    }

    public boolean processadaComSucesso() {
        return !transacoesConcluidasComSucesso().isEmpty();

    }
}
