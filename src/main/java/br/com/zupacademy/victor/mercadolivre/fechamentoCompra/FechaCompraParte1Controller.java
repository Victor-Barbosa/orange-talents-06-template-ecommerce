package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.produto.ProdutoRepository;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;
import br.com.zupacademy.victor.mercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fechacompra")
public class FechaCompraParte1Controller {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CompraRepository compraRepository;

    private Produto buscaProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe produto com esse id"));
    }

    @PostMapping
    public String novaCompra(@Valid @RequestBody NovaCompraRequest novaCompraRequest, @AuthenticationPrincipal Usuario usuarioLogado,
                             UriComponentsBuilder uriComponentsBuilder) throws BindException {
        Usuario usuario = usuarioRepository.getById(usuarioLogado.getId());
        Produto produto = buscaProdutoPorId(novaCompraRequest.getIdProduto());

        int quantidade = novaCompraRequest.getQuantidade();
        boolean abateu = produto.abateEstoque(novaCompraRequest.getQuantidade());
        if (abateu){
            GatewayPagamento gateway = novaCompraRequest.getGateway();
            Compra novaCompra = new Compra(usuario, produto, quantidade, novaCompraRequest.getGateway());
            compraRepository.save(novaCompra);
            if (gateway.equals(GatewayPagamento.pagseguro)){
                String urlRetornoPagSeguro = String.valueOf(uriComponentsBuilder.path("retorno-pagseguro/{id}")
                        .buildAndExpand(novaCompra.getId().toString()));

                return  "pagseguro.com/" + novaCompra.getId() + "?redirectUrl=" + urlRetornoPagSeguro;

            } else {
                String urlRetornoPayPal = String.valueOf(uriComponentsBuilder.path("retorno-paypal/{id}")
                        .buildAndExpand(novaCompra.getId().toString()));

                return  "paypal.com/" + novaCompra.getId() + "?redirectUrl=" + urlRetornoPayPal;

            }
        }
        BindException problemaComEstoque = new BindException(novaCompraRequest, "novaCompraRequest");
        problemaComEstoque.reject(null, "Não foi possivel realizar a compra por conta do estoque!");

        throw problemaComEstoque;
    }
}
