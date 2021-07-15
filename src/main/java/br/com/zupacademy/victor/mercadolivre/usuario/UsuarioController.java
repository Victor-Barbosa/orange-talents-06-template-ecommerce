package br.com.zupacademy.victor.mercadolivre.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/vi/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public void cadastraUsuario (@Valid @RequestBody CadastraUsuarioRequest cadastraUsuarioRequest){
        Usuario novoUsuario = cadastraUsuarioRequest.toModel();
        usuarioRepository.save(novoUsuario);
    }
}
