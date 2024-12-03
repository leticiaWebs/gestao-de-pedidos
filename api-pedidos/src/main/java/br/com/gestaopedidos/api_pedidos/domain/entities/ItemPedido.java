package br.com.gestaopedidos.api_pedidos.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ItemPedido {

    private Long id;
    private String produtoId;
    private String descricao;
    private Integer quantidade;
    private BigDecimal preco;
}
