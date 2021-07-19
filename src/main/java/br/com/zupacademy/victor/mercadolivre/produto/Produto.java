package br.com.zupacademy.victor.mercadolivre.produto;

import br.com.zupacademy.victor.mercadolivre.categoria.Categoria;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private BigDecimal valor;
    @Column(nullable = false)
    private int quantidade;
    @Column(nullable = false)
    @Size(max = 1000)
    private String descricao;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();
    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();


    public Produto(@NotBlank String nome, @Positive BigDecimal valor, @Positive int quantidade, @NotBlank @Length(max = 1000) String descricao,
                   Categoria categoria, Usuario usuario, @Size(min = 3) @Valid Collection<NovaCaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.caracteristicas.addAll(caracteristicas.stream().map(caracteristica
                -> caracteristica.toModel(this)).collect(Collectors.toSet()));
        Assert.isTrue(this.caracteristicas.size() >= 3, "Todo produto precisa ter no mínimo 3 ou mais caracteristicas");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return Objects.equals(caracteristicas, produto.caracteristicas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caracteristicas);
    }

    @Deprecated
    public Produto() {

    }

    public void associaImagens(Set<String> links) {
        Set<ImagemProduto> imagens = links.stream().map(link -> new ImagemProduto(this, link)).collect(Collectors.toSet());
        this.imagens.addAll(imagens);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                ", usuario=" + usuario +
                ", caracteristicas=" + caracteristicas +
                ", imagens=" + imagens +
                '}';
    }

    public boolean pertenceAoUsuario(Usuario possivelDono) {
        return this.usuario.equals(possivelDono);
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
