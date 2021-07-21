package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventosNovaCompra {

    @Autowired
    private Set<EventoCompraSucesso> eventosCompraSucesso;
    @Autowired
    private Set<EventoCompraFalha> eventosCompraFalhas;

    public void processa(Compra compra) {

        if (compra.processadaComSucesso()){
            eventosCompraSucesso.forEach(evento -> evento.processa(compra));
        }
        else {
            eventosCompraFalhas.forEach(evento -> evento.processa(compra));

        }
    }
}
