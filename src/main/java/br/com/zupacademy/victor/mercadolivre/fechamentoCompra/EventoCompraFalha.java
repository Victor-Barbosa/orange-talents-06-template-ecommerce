package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

/**
 * todo evento relacionado a falha de uma nova compra deve implementar essa interface
 */
public interface EventoCompraFalha {

    void processa(Compra compra);
}
