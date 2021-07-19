package br.com.zupacademy.victor.mercadolivre.adicionaPergunta;

import br.com.zupacademy.victor.mercadolivre.compartilhado.Emails;
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
public class NovaPerguntaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private Emails emails;

    private Produto buscaProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe produto com esse id"));
    }

    @PostMapping("/{id}/perguntas")
    public void adicionaPergunta (@PathVariable("id") Long id, @Valid @RequestBody NovaPerguntaRequest novaPerguntaRequest,
                                  @AuthenticationPrincipal Usuario usuarioLogado){

        Usuario usuario = usuarioRepository.getById(usuarioLogado.getId());
        Produto produto = buscaProdutoPorId(id);
        Pergunta novaPergunta = novaPerguntaRequest.toModel(usuario, produto);
        perguntaRepository.save(novaPergunta);

        emails.novaPergunta(novaPergunta);
    }
}
