package br.com.zupacademy.victor.mercadolivre.outrosSistemas;

import br.com.zupacademy.victor.mercadolivre.compartilhado.Emails;
import br.com.zupacademy.victor.mercadolivre.fechamentoCompra.Compra;
import br.com.zupacademy.victor.mercadolivre.fechamentoCompra.EventoCompraFalha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailFalhaNovaCompra implements EventoCompraFalha {

    @Autowired
    private Emails emails;

    @Override
    public void processa(Compra compra) {
        emails.falhaCompra(compra);
    }
}
