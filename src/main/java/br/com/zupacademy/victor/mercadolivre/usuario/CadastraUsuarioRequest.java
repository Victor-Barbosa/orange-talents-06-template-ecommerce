package br.com.zupacademy.victor.mercadolivre.usuario;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CadastraUsuarioRequest {

    @NotBlank(message = "Email Obrigatório!")
    @Email(message = "Email deve ser em formato válido!")
    private String email;
    @NotBlank(message = "Senha Obrigatória!")
    @Length(min = 6, message = "senha deve conter no minimo 6 caracteres")
    private String senha;

    public CadastraUsuarioRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
    public Usuario toModel() {
        return new Usuario(email, senha);
    }
}
