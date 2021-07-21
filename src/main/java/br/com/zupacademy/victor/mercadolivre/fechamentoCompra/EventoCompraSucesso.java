package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

/**
 * todo evento relacionado ao sucesso de uma nova compra deve implementar essa interface
 */
public interface EventoCompraSucesso {

    void processa(Compra compra);
}
