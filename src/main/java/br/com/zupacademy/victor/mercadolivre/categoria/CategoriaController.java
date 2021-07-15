package br.com.zupacademy.victor.mercadolivre.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public void cadastraCategoria (@Valid @RequestBody CadastraCategoriaRequest cadastraCategoriaRequest){
        if (categoriaRepository.existsByNome(cadastraCategoriaRequest.getNome())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe categoria com esse nome!");
        }

        Categoria novaCategoria = cadastraCategoriaRequest.toModel();
        categoriaRepository.save(novaCategoria);
    }
}
