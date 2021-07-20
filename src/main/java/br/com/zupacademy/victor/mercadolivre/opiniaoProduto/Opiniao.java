package br.com.zupacademy.victor.mercadolivre.opiniaoProduto;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Opiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(1)
    @Max(5)
    private int nota;
    private String titulo;
    private String descricao;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Produto produto;

    public Opiniao(@Min(1) @Max(5) int nota, @NotBlank String titulo, @NotBlank @Size(max = 500) String descricao, Usuario usuario, Produto produto) {

        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.produto = produto;
    }

    public int getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    @Deprecated
    public Opiniao() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Opiniao)) return false;
        Opiniao opiniao = (Opiniao) o;
        return Objects.equals(titulo, opiniao.titulo) && Objects.equals(descricao, opiniao.descricao) && Objects.equals(usuario, opiniao.usuario) && Objects.equals(produto, opiniao.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, descricao, usuario, produto);
    }
}
