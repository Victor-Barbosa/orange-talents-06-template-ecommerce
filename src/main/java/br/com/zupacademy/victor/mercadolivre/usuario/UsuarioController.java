package br.com.zupacademy.victor.mercadolivre.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/vi/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public void cadastraUsuario (@Valid @RequestBody CadastraUsuarioRequest cadastraUsuarioRequest){

        if (usuarioRepository.existsByEmail(cadastraUsuarioRequest.email)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe usuário com esse email cadastrado!");
        }
        Usuario novoUsuario = cadastraUsuarioRequest.toModel();
        usuarioRepository.save(novoUsuario);
    }
}
