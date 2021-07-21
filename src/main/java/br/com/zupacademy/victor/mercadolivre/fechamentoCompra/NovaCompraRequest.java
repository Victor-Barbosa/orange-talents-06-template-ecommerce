package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovaCompraRequest {

    @Positive
    private int quantidade;
    @NotNull
    private Long idProduto;
    @NotNull
    private GatewayPagamento gatewayPagamento;

    public NovaCompraRequest(int quantidade, Long idProduto, GatewayPagamento gatewayPagamento) {
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.gatewayPagamento = gatewayPagamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public GatewayPagamento getGateway() {
        return gatewayPagamento;
    }
}
