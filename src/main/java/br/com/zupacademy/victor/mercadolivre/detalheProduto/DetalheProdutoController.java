package br.com.zupacademy.victor.mercadolivre.detalheProduto;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/produtos")
public class DetalheProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto buscaProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe produto com esse id"));
    }

    @GetMapping("/{id}")
    public DetalheProdutoDto detalhesProduto(@PathVariable("id") Long id){
        Produto produtoEscolhido = buscaProdutoPorId(id);
        return new DetalheProdutoDto(produtoEscolhido);
    }
}
