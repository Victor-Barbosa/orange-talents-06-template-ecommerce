package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

public interface RetornoGatewayPagamento {

    /**
     *
     * @param compra
     * @return uma nova transacao em função do gateway especifico
     */
    Transacao toTransacao(Compra compra);
}
