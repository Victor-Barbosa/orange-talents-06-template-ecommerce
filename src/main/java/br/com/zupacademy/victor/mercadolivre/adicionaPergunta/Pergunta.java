package br.com.zupacademy.victor.mercadolivre.adicionaPergunta;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Pergunta implements Comparable<Pergunta>{

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

    public String getTitulo() {
        return titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pergunta)) return false;
        Pergunta pergunta = (Pergunta) o;
        return Objects.equals(titulo, pergunta.titulo) && Objects.equals(usuario, pergunta.usuario) && Objects.equals(produto, pergunta.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, usuario, produto);
    }

    @Override
    public int compareTo(Pergunta o) {
        return this.titulo.compareTo(o.titulo);
    }
}
