package br.com.zupacademy.victor.mercadolivre.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime horaDoCadastro = LocalDateTime.now();

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    @Size(min = 6)
    private String senha;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }

    @Deprecated
    public Usuario() {
    }
}
