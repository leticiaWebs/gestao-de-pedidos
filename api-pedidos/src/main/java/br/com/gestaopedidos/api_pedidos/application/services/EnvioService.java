package br.com.gestaopedidos.api_pedidos.application.services;


import br.com.gestaopedidos.api_pedidos.application.dtos.PedidosDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnvioService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void envioDadosPedido(Long pedidoId) {
        kafkaTemplate.send("pedidos", "id.novopedido", pedidoId.toString());
    }
}
