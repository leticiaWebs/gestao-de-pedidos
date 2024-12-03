package br.com.gestaopedidos.api_pedidos.application.services;

import br.com.gestaopedidos.api_pedidos.application.dtos.PedidosDTO;
import br.com.gestaopedidos.api_pedidos.application.services.exceptions.ResourceNotFoundException;
import br.com.gestaopedidos.api_pedidos.domain.entities.ItemPedido;
import br.com.gestaopedidos.api_pedidos.domain.entities.Pedido;
import br.com.gestaopedidos.api_pedidos.domain.enums.StatusPedido;
import br.com.gestaopedidos.api_pedidos.domain.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }


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
        Pedido entity = new Pedido();
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
        List<Pedido> list  = pedidoRepository.findAll();
        return list.stream().map(x -> new PedidosDTO(x)).collect(Collectors.toList());
    }

    public PedidosDTO findById(Long id) {
        Optional<Pedido> obj = pedidoRepository.findById(id);
        Pedido entity = obj.orElseThrow(() -> new ResourceNotFoundException("Pedido  nao encontrado"));
        return new PedidosDTO(entity);
    }

    @Transactional
    public PedidosDTO update(Long id, PedidosDTO dto) {
        Pedido entity = pedidoRepository.getReferenceById(id);
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


    public void atualizarStatus(String pagamentoId, StatusPedido status) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findByPagamentoId(pagamentoId);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setStatus(status);
            pedidoRepository.save(pedido);
        }
    }

//    public void removerEstoque()
}
