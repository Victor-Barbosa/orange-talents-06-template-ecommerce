package br.com.zupacademy.victor.mercadolivre.adicionaPergunta;

import br.com.zupacademy.victor.mercadolivre.produto.Produto;
import br.com.zupacademy.victor.mercadolivre.usuario.Usuario;

import javax.validation.constraints.NotBlank;

public class NovaPerguntaRequest {

    @NotBlank
    private String titulo;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pergunta toModel(Usuario usuario, Produto produto) {
        return new Pergunta(titulo, usuario, produto);
    }
}
