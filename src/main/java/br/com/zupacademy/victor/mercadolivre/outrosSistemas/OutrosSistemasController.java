package br.com.zupacademy.victor.mercadolivre.outrosSistemas;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OutrosSistemasController {

    @Transactional
    @PostMapping("/notas-fiscais")
    public void criaNota (@Valid @RequestBody NovaCompraNFRequest request) throws InterruptedException{
        System.out.println("Criando nota para " + request);
        Thread.sleep(150);
    }

    @Transactional
    @PostMapping("/ranking")
    public void ranking (@Valid @RequestBody RankingNovaCompraRequest request) throws InterruptedException{
        System.out.println("Criando ranking " + request);
        Thread.sleep(150);
    }
}
