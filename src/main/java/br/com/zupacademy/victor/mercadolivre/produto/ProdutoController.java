package br.com.zupacademy.victor.mercadolivre.produto;

import br.com.zupacademy.victor.mercadolivre.categoria.Categoria;
import br.com.zupacademy.victor.mercadolivre.categoria.CategoriaRepository;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;
import br.com.zupacademy.victor.mercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/produto")
public class ProdutoController {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Categoria buscaCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe Categoria com esse id no sistema!"));
    }

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new ProibiCaracteristicaComNomeIguialValidator());

    }

    @PostMapping
    public String cadastraProduto (@Valid @RequestBody CadastraProdutoRequest cadastraProdutoRequest, @AuthenticationPrincipal Usuario usuarioLogado){
        Usuario usuario = usuarioRepository.getById(usuarioLogado.getId());
        Categoria categoria = buscaCategoriaPorId(cadastraProdutoRequest.getIdCategoria());
        Produto novoProduto = cadastraProdutoRequest.ToModel(categoria, usuario);
        produtoRepository.save(novoProduto);

        return cadastraProdutoRequest.toString();
    }




}
