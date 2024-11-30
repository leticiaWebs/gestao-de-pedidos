package br.com.gestaopedidos.api_pedidos.adapters.mocks;

import br.com.gestaopedidos.api_pedidos.application.dtos.WebhookPagamentoDTO;
import br.com.gestaopedidos.api_pedidos.domain.enums.StatusPedido;
import org.springframework.stereotype.Component;


@Component
public class MockPagamentoService {

    public WebhookPagamentoDTO enviarPagamento(String pagamentoId) {
        return new WebhookPagamentoDTO(pagamentoId, StatusPedido.PAGO);
    }
}
