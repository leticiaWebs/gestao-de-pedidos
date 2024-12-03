package br.com.gestaopedidos.api_pedidos.adapters.controllers;

import br.com.gestaopedidos.api_pedidos.application.dtos.PedidosDTO;
import br.com.gestaopedidos.api_pedidos.application.services.EnvioService;
import br.com.gestaopedidos.api_pedidos.application.services.PedidoService;
import br.com.gestaopedidos.api_pedidos.domain.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping(value ="/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private  EnvioService envioservice;

    @PostMapping
     public ResponseEntity<PedidosDTO> insert(@RequestBody PedidosDTO dto) {
        dto = pedidoService.insert(dto);
        URI uri = URI.create("/clientes/" + dto.getId());
        envioservice.envioDadosPedido(dto.getId());
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<PedidosDTO>> findAll() {
        List <PedidosDTO> list = pedidoService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidosDTO> findById(@PathVariable Long id) {
        System.out.println("Recebendo requisição para buscar pedido com ID: " + id);
        PedidosDTO dto = pedidoService.findById(id);
        System.out.println("Retornando pedido: " + dto);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidosDTO> update(@PathVariable Long id, @RequestBody PedidosDTO dto) {
        dto = pedidoService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}
