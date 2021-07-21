package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fechacompra2")
public class FechaCompraParte2Controller {

    @Autowired
    private CompraRepository compraRepository;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private EventosNovaCompra eventosNovaCompra;

    @Transactional
    @PostMapping("/retorno-pagseguro/{id}")
    public String processamentoPagSeguro (@PathVariable("id") Long idCompra, @Valid  RetornoPagSeguroRequest request){
        return processa(idCompra, request);
    }

    @Transactional
    @PostMapping("/retorno-paypal/{id}")
    public String processamentoPaypal (@PathVariable("id") Long idCompra, @Valid  RetornoPaypalRequest request){
        return processa(idCompra, request);
    }

    private String processa(Long idCompra, RetornoGatewayPagamento retornoGatewayPagamento){
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe compra com esse id!"));
        compra.adcionaTransacao(retornoGatewayPagamento);
        manager.merge(compra);

        eventosNovaCompra.processa(compra);

        return compra.toString();

    }


}
