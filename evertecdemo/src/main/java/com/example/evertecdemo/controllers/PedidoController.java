package com.example.evertecdemo.controllers;

import com.example.evertecdemo.dto.PedidoDTO;
import com.example.evertecdemo.models.EstadoPedido;
import com.example.evertecdemo.services.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/nuevo")
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO nuevoPedido = pedidoService.crearPedido(pedidoDTO);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PedidoDTO>> listarTodosLosPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodosLosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.obtenerPedido(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosCliente(@PathVariable Long clienteId) {
        List<PedidoDTO> pedidos = pedidoService.listarPedidosCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstadoPedido(
            @PathVariable Long id,
            @RequestBody Map<String, String> estado) {
        try {
            EstadoPedido nuevoEstado = EstadoPedido.valueOf(estado.get("estado").toUpperCase());
            PedidoDTO pedidoActualizado = pedidoService.actualizarEstadoPedido(id, nuevoEstado);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.ok("Se ha cancelado el pedido con ID: " + id);
    }
}