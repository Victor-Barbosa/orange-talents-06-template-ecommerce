package br.com.zupacademy.victor.mercadolivre.categoria;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class CadastraCategoriaRequest {

    @NotBlank(message = "Nome da Categoria não pode ser em branco!")
    @Length(min = 3)
    private String nome;

    private Long idCategoriaMae;

    public Categoria toModel() {
        return new Categoria(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
