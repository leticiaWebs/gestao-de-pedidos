package br.com.gestaopedidos.api_pedidos.domain.repositories;

import br.com.gestaopedidos.api_pedidos.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findById(Long id);

    Optional<Pedido> findByPagamentoId(String pagamentoId);
}
