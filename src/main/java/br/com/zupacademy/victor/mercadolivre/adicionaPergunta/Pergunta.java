package br.com.zupacademy.victor.mercadolivre.adicionaPergunta;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private final LocalDateTime instanteCriacao = LocalDateTime.now();
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Produto produto;

    public Pergunta(@NotBlank String titulo, Usuario usuario, Produto produto) {

        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
    }

    @Deprecated
    public Pergunta() {

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }
}
