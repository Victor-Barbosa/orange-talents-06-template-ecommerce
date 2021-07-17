package br.com.zupacademy.victor.mercadolivre.produto;

import br.com.zupacademy.victor.mercadolivre.categoria.Categoria;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CadastraProdutoRequest {

    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private BigDecimal valor;
    @Positive
    private int quantidade;
    @NotBlank
    @Length(max = 1000)
    private String descricao;
    @NotNull
    private Long idCategoria;
    @Size(min = 3)
    @Valid
    private List<NovaCaracteristicaRequest> caracteristicas = new ArrayList<>();

    public CadastraProdutoRequest(String nome, BigDecimal valor, int quantidade, String descricao, Long idCategoria, List<NovaCaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.caracteristicas.addAll(caracteristicas);
    }

    public List<NovaCaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public Produto ToModel(Categoria categoria, Usuario usuario) {
        return new Produto(nome, valor, quantidade, descricao, categoria, usuario, caracteristicas);
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public Set<String> buscaCaracteristicasIguais() {
        HashSet<String> nomesIguais = new HashSet<>();
        HashSet<String> resultados = new HashSet<>();
        for (NovaCaracteristicaRequest caracteristica : caracteristicas){
            String nome = caracteristica.getNome();
            if (!nomesIguais.add(nome)){
                resultados.add(nome);
            }
        }
        return resultados;
    }

    @Override
    public String toString() {
        return "CadastraProdutoRequest{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", idCategoria=" + idCategoria +
                ", caractristicas=" + caracteristicas +
                '}';
    }
}
