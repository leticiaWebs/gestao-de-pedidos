package br.com.gestaopedidos.api_pedidos.domain.services;

import br.com.gestaopedidos.api_pedidos.application.dtos.PedidosDTO;
import br.com.gestaopedidos.api_pedidos.domain.entities.ItemPedido;
import br.com.gestaopedidos.api_pedidos.domain.entities.Pedidos;
import br.com.gestaopedidos.api_pedidos.domain.services.exceptions.ResourceNotFoundException;
import br.com.gestaopedidos.api_pedidos.repository.PedidosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidoRepository;

    public BigDecimal calcularValorTotal(List<ItemPedido> itensPedido) {
        if (itensPedido == null || itensPedido.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return itensPedido.stream()
                .map(item -> item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public PedidosDTO insert(PedidosDTO dto) {
        Pedidos entity = new Pedidos();
        entity.setId(dto.getId());
        entity.setCliente(dto.getCliente());
        entity.setTotal(dto.getTotal());
        entity.setStatus(dto.getStatus());
        entity.setItens(dto.getItens());

        BigDecimal total = calcularValorTotal(dto.getItens());
        entity.setTotal(String.valueOf(total));
        entity.setVersion(dto.getVersion());
        entity.setPagamentoId(dto.getPagamentoId());
        entity = pedidoRepository.save(entity);
        return new PedidosDTO(entity);
    }

    @Transactional
    public List<PedidosDTO> findAll() {
        List<Pedidos> list  = pedidoRepository.findAll();
        return list.stream().map(x -> new PedidosDTO(x)).collect(Collectors.toList());
    }

    public PedidosDTO findById(Long id) {
        Optional<Pedidos> obj = pedidoRepository.findById(id);
        Pedidos entity = obj.orElseThrow(() -> new ResourceNotFoundException("Pedido  nao encontrado"));
        return new PedidosDTO(entity);
    }

    @Transactional
    public PedidosDTO update(Long id, PedidosDTO dto) {
        Pedidos entity = pedidoRepository.getReferenceById(id);
        entity.setStatus(dto.getStatus());
        entity.setItens(dto.getItens());

        BigDecimal novoTotal = dto.getItens().stream()
                .map(item -> item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        entity.setTotal(String.valueOf(novoTotal));

        entity.setVersion(dto.getVersion());
        entity = pedidoRepository.save(entity);
        return new PedidosDTO(entity);
    }

}
