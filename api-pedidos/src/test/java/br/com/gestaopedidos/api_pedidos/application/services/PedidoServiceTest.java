package br.com.gestaopedidos.api_pedidos.application.services;

import br.com.gestaopedidos.api_pedidos.application.dtos.PedidosDTO;
import br.com.gestaopedidos.api_pedidos.application.services.exceptions.ResourceNotFoundException;
import br.com.gestaopedidos.api_pedidos.domain.entities.ItemPedido;
import br.com.gestaopedidos.api_pedidos.domain.entities.Pedido;
import br.com.gestaopedidos.api_pedidos.domain.enums.StatusPedido;
import br.com.gestaopedidos.api_pedidos.domain.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class PedidoServiceTest {
    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalcularValorTotal_WithValidItems() {
        // Arrange
        ItemPedido item1 = new ItemPedido();
        item1.setPreco(new BigDecimal("10.00"));
        item1.setQuantidade(2);

        ItemPedido item2 = new ItemPedido();
        item2.setPreco(new BigDecimal("5.00"));
        item2.setQuantidade(3);

        List<ItemPedido> itensPedido = Arrays.asList(item1, item2);

        // Act
        BigDecimal total = pedidoService.calcularValorTotal(itensPedido);

        // Assert
        assertEquals(new BigDecimal("35.00"), total);
    }

    @Test
    void testCalcularValorTotal_WithEmptyList() {
        // Act
        BigDecimal total = pedidoService.calcularValorTotal(Arrays.asList());

        // Assert
        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    void testInsert() {
        // Arrange
        ItemPedido item = new ItemPedido();
        item.setPreco(new BigDecimal("10.00"));
        item.setQuantidade(2);

        PedidosDTO dto = new PedidosDTO();
        dto.setItens(Arrays.asList(item));

        Pedido pedido = new Pedido();
        pedido.setId(1L);

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // Act
        PedidosDTO result = pedidoService.insert(dto);

        // Assert
        assertEquals(1L, result.getId());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testFindById_WithExistingId() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente("John Doe");

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        // Act
        PedidosDTO result = pedidoService.findById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getCliente());
    }

    @Test
    void testFindById_WithNonExistingId() {
        // Arrange
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> pedidoService.findById(1L));
    }

    @Test
    void testAtualizarStatus_WithExistingPedido() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(StatusPedido.PENDENTE);

        when(pedidoRepository.findByPagamentoId("123")).thenReturn(Optional.of(pedido));

        // Act
        pedidoService.atualizarStatus("123", StatusPedido.PAGO);

        // Assert
        assertEquals(StatusPedido.PAGO, pedido.getStatus());
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    void testAtualizarStatus_WithNonExistingPedido() {
        // Arrange
        when(pedidoRepository.findByPagamentoId("123")).thenReturn(Optional.empty());

        // Act
        pedidoService.atualizarStatus("123", StatusPedido.PAGO);

        // Assert
        verify(pedidoRepository, never()).save(any());
    }
}
