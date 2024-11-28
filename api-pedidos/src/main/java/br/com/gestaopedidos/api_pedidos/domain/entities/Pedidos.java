package br.com.gestaopedidos.api_pedidos.domain.entities;

import br.com.gestaopedidos.api_pedidos.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_pedidos")
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cliente;
    private String  total;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private List<ItemPedido> itens;
    @Version
    private Long version;
    private String pagamentoId;

}
