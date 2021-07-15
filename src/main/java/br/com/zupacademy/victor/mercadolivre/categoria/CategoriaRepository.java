package br.com.zupacademy.victor.mercadolivre.categoria;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


    boolean existsByNome(String nome);
}