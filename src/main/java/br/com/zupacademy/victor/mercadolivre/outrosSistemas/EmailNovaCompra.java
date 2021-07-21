package br.com.zupacademy.victor.mercadolivre.outrosSistemas;

import br.com.zupacademy.victor.mercadolivre.compartilhado.Emails;
import br.com.zupacademy.victor.mercadolivre.fechamentoCompra.Compra;
import br.com.zupacademy.victor.mercadolivre.fechamentoCompra.EventoCompraSucesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailNovaCompra implements EventoCompraSucesso {

    @Autowired
    private Emails emails;

    @Override
    public void processa(Compra compra) {
        emails.compraSucesso(compra);
    }
}
