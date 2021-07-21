package br.com.zupacademy.victor.mercadolivre.compartilhado;

import br.com.zupacademy.victor.mercadolivre.adicionaPergunta.Pergunta;
import br.com.zupacademy.victor.mercadolivre.fechamentoCompra.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class Emails {

    @Autowired
    private Mailer mailer;

    public void novaPergunta(@NotNull @Valid Pergunta pergunta) {
        mailer.send("<html>...</html>", "Nova pergunta...", pergunta.getUsuario().getEmail(),
                "novapergunta@nossomercadolivre.com", pergunta.getProduto().getUsuario().getEmail());
    }

    public void novaCompra(Compra novaCompra) {
        mailer.send("nova compra..." + novaCompra, "Você tem uma nova compra",
                novaCompra.getUsuario().getEmail(),
                "compras@nossomercadolivre.com",
                novaCompra.getDonoProduto().getEmail());
    }

    public void compraSucesso(Compra compra) {
        mailer.send("Sua compra do produto: " + compra.getProduto().getNome(), "foi realizada com sucesso",
                compra.getUsuario().getEmail(),
                "compras@nossomercadolivre.com,",
                "");

    }

    public void falhaCompra(Compra compra) {
        mailer.send("Sua compra falhou " + compra.getProduto().getNome(), "Voce pode tentar novamente clicando no link no final.",
                "compras@nossomercadolivre.com", compra.getUsuario().getEmail(), "link");
    }
}
