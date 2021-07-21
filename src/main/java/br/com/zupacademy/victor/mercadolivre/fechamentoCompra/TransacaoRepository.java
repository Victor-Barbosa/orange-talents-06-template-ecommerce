package br.com.zupacademy.victor.mercadolivre.fechamentoCompra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
