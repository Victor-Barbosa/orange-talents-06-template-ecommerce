package br.com.zupacademy.victor.mercadolivre.produto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ProibiCaracteristicaComNomeIguialValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CadastraProdutoRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()){
            return;
        }
        CadastraProdutoRequest request = (CadastraProdutoRequest) target;
        Set<String> nomesIguais = request.buscaCaracteristicasIguais();
        if (!nomesIguais.isEmpty()){
            errors.rejectValue("caracteristicas",null, "Não pode ter duas caracteristicas iguais!" + nomesIguais);

        }
    }
}
