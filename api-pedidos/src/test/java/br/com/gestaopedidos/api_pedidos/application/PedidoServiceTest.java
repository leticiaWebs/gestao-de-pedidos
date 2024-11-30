package br.com.gestaopedidos.api_pedidos.application;

import br.com.gestaopedidos.api_pedidos.application.services.PedidoService;
import br.com.gestaopedidos.api_pedidos.domain.entities.ItemPedido;
import br.com.gestaopedidos.api_pedidos.domain.entities.Pedido;
import br.com.gestaopedidos.api_pedidos.domain.enums.StatusPedido;
import br.com.gestaopedidos.api_pedidos.domain.repositories.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    private PedidoRepository pedidoRepository = mock(PedidoRepository.class);
    private PedidoService pedidoService = new PedidoService(pedidoRepository);

    @Test
    void testAtualizarStatus() {
        // Configuração do pedido inicial
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente("Cliente Teste");
        pedido.setTotal("100.00");
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setPagamentoId("123");

        // Configuração dos itens do pedido
        ItemPedido item = new ItemPedido();
        item.setId(1L);
        item.setProdutoId("prod-001");
        item.setDescricao("Produto Teste");
        item.setQuantidade(2);
        item.setPreco(new BigDecimal("50.00"));
        pedido.setItens(Collections.singletonList(item));

        // Mock do repositório
        when(pedidoRepository.findByPagamentoId("123")).thenReturn(Optional.of(pedido));

        // Execução do método a ser testado
        pedidoService.atualizarStatus("123", StatusPedido.PAGO);

        // Verificação se o status foi atualizado e salvo corretamente
        verify(pedidoRepository, times(1)).save(Mockito.argThat(p ->
                p.getStatus() == StatusPedido.PAGO &&
                        p.getPagamentoId().equals("123")
        ));
    }
}
