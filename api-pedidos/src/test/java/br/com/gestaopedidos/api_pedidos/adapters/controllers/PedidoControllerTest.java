package br.com.gestaopedidos.api_pedidos.adapters.controllers;

import br.com.gestaopedidos.api_pedidos.application.dtos.PedidosDTO;
import br.com.gestaopedidos.api_pedidos.application.services.EnvioService;
import br.com.gestaopedidos.api_pedidos.application.services.PedidoService;
import br.com.gestaopedidos.api_pedidos.domain.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PedidoControllerTest {


    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsert() {
        // Arrange
        PedidosDTO dto = new PedidosDTO();
        dto.setId(1L);
        when(pedidoService.insert(any(PedidosDTO.class))).thenReturn(dto);

        // Act
        ResponseEntity<PedidosDTO> response = pedidoController.insert(dto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/clientes/1", response.getHeaders().getLocation().toString());
        assertEquals(dto, response.getBody());
        verify(envioService, times(1)).envioDadosPedido(1L);
    }

    @Test
    void testFindAll() {
        // Arrange
        PedidosDTO pedido1 = new PedidosDTO();
        PedidosDTO pedido2 = new PedidosDTO();
        List<PedidosDTO> pedidos = Arrays.asList(pedido1, pedido2);
        when(pedidoService.findAll()).thenReturn(pedidos);

        // Act
        ResponseEntity<List<PedidosDTO>> response = pedidoController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidos, response.getBody());
    }

    @Test
    void testFindById() {
        // Arrange
        PedidosDTO dto = new PedidosDTO();
        dto.setId(1L);
        when(pedidoService.findById(1L)).thenReturn(dto);

        // Act
        ResponseEntity<PedidosDTO> response = pedidoController.findById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdate() {
        // Arrange
        PedidosDTO dto = new PedidosDTO();
        dto.setId(1L);
        when(pedidoService.update(eq(1L), any(PedidosDTO.class))).thenReturn(dto);

        // Act
        ResponseEntity<PedidosDTO> response = pedidoController.update(1L, dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }
}
