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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("api/v1/produto")
public class ProdutoController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UploaderFake uploaderFake;

    private Categoria buscaCategoriaPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe Categoria com esse id no sistema!"));
    }

    private Produto buscaProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe produto com esse id"));
    }

    @InitBinder(value = "cadastraProdutoRequest")
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

    @PostMapping(value = "/{id}/imagens")
    @Transactional
    public String adicionarImagens (@PathVariable("id") Long id, @Valid NovasImagensRequest novasImagensRequest,
                                    @AuthenticationPrincipal Usuario usuarioLogado){
        /*
        * 1) enviar imagens para o local onde elas vão ficar
        * 2) pegar os links de todas as imagens
        * 3) associar esses links com o produto em questão
        * 4) preciso carregar o produto
        * 5) depois que associar eu preciso atualizar a versão do produto
         */
        Usuario usuario = usuarioRepository.getById(usuarioLogado.getId());
        Produto produto = buscaProdutoPorId(id);

        if (!produto.pertenceAoUsuario(usuario)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode adicionar imagens a um produto que vc cadastrou!");
        }

        Set<String> links = uploaderFake.envia(novasImagensRequest.getImagens());
        produto.associaImagens(links);
        produtoRepository.save(produto);

        return produto.toString();
    }




}
