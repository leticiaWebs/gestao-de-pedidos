package br.com.gestaopedidos.api_pedidos.application.dtos;

import br.com.gestaopedidos.api_pedidos.domain.enums.StatusPedido;

public class WebhookPagamentoDTO {

    private String pagamentoId;
    private StatusPedido status;

    public WebhookPagamentoDTO() {}

    public WebhookPagamentoDTO(String pagamentoId, StatusPedido status) {
        this.pagamentoId = pagamentoId;
        this.status = status;
    }

    public String getPagamentoId() {
        return pagamentoId;
    }

    public void setPagamentoId(String pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }
}