package br.com.zupacademy.victor.mercadolivre.opiniaoProduto;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.produto.ProdutoRepository;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;
import br.com.zupacademy.victor.mercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/produto")
public class AdicionaOpiniaoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private OpiniaoRepository opiniaoRepository;

    private Produto buscaProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe produto com esse id"));
    }

    @PostMapping("{id}/opiniao")
    public String adicionaOpiniao (@PathVariable("id") Long id, @Valid @RequestBody NovaOpiniaoRequest novaOpiniaoRequest,
                                 @AuthenticationPrincipal Usuario usuarioLogado){
        Usuario usuario = usuarioRepository.getById(usuarioLogado.getId());
        Produto produto = buscaProdutoPorId(id);

        Opiniao novaOpiniao = novaOpiniaoRequest.toModel(usuario, produto);
        opiniaoRepository.save(novaOpiniao);

        return novaOpiniaoRequest.toString();
    }
}
