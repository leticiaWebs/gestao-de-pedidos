package br.com.gestaopedidos.api_pedidos.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EnvioServiceTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private EnvioService envioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnvioDadosPedido() {
        // Arrange
        Long pedidoId = 123L;
        String topic = "pedidos";
        String key = "id.novopedido";
        String value = pedidoId.toString();

        // Act
        envioService.envioDadosPedido(pedidoId);

        // Assert
        verify(kafkaTemplate, times(1)).send(eq(topic), eq(key), eq(value));
    }
}
