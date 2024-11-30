package br.com.gestaopedidos.api_pedidos.adapters.controllers;

import br.com.gestaopedidos.api_pedidos.application.dtos.WebhookPagamentoDTO;
import br.com.gestaopedidos.api_pedidos.application.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<String> receberWebhook(@RequestBody WebhookPagamentoDTO webhook) {
        pedidoService.atualizarStatus(webhook.getPagamentoId(), webhook.getStatus());
        return ResponseEntity.ok("Pagamento processado com sucesso!");
    }
}
