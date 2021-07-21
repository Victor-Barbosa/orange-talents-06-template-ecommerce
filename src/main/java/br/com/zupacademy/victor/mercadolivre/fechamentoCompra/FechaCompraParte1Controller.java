package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import br.com.zupacademy.victor.mercadolivre.compartilhado.Emails;
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
@RequestMapping("/api/v1/fechacompra1")
public class FechaCompraParte1Controller {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private Emails emails;

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
            Compra novaCompra = new Compra(usuario, produto, quantidade, gateway);
            compraRepository.save(novaCompra);
            emails.novaCompra(novaCompra);

            return novaCompra.urlRedirecionamento(uriComponentsBuilder);

        }

        BindException problemaComEstoque = new BindException(novaCompraRequest, "novaCompraRequest");
        problemaComEstoque.reject(null, "Não foi possivel realizar a compra por conta do estoque!");

        throw problemaComEstoque;
    }
}
