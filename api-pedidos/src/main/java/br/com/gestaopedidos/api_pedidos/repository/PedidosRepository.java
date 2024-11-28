package br.com.gestaopedidos.api_pedidos.repository;

import br.com.gestaopedidos.api_pedidos.domain.entities.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
    Optional<Pedidos> findById(Long id);

}
