package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class Ranking implements EventoCompraSucesso{

    @Override
    public void processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(), "isso não devia acontecer, erro no ranking");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", compra.getId(), "idDonoProduto", compra.getProduto().getUsuario().getId());

        restTemplate.postForEntity("http://localhost:8080/ranking", request, String.class);
    }
}
