package br.com.gestaopedidos.api_pedidos.application.dtos;

import br.com.gestaopedidos.api_pedidos.domain.entities.ItemPedido;
import br.com.gestaopedidos.api_pedidos.domain.entities.Pedido;
import br.com.gestaopedidos.api_pedidos.domain.enums.StatusPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PedidosDTO implements Serializable {

    private Long id;
    private String cliente;
    private String  total;
    private StatusPedido status;
    private List<ItemPedido> itens;
    private Long version;
    private String pagamentoId;


    public PedidosDTO(Long id, String cliente, String total, StatusPedido status, List<ItemPedido> itens, Long version, String pagamentoId) {
        this.id = id;
        this.cliente = cliente;
        this.total = total;
        this.status = status;
        this.itens = itens;
        this.version = version;
        this.pagamentoId = pagamentoId;


    }
    public PedidosDTO(Pedido entity) {
        this.id = entity.getId();
        this.cliente = entity.getCliente();
        this.total = entity.getTotal();
        this.status = entity.getStatus();
        this.itens = entity.getItens();
        this.version = entity.getVersion();
        this.pagamentoId = entity.getPagamentoId();
    }
}
