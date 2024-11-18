package com.example.evertecdemo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.evertecdemo.dto.PedidoDTO;
import com.example.evertecdemo.models.EstadoPedido;
import com.example.evertecdemo.services.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Endpoint para crear un nuevo pedido
    @PostMapping("/nuevo")
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO nuevoPedido = pedidoService.crearPedido(pedidoDTO);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    // Endpoint para listar todos los pedidos de un cliente específico
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> listarPedidosCliente(@PathVariable Long clienteId) {
        List<PedidoDTO> pedidos = pedidoService.listarPedidosCliente(clienteId);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Endpoint para actualizar el estado de un pedido con path
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> actualizarEstadoPedido(
            @PathVariable Long id,
            @RequestParam EstadoPedido estado) {

        PedidoDTO pedidoActualizado = pedidoService.actualizarEstadoPedido(id, estado);
        return new ResponseEntity<>(pedidoActualizado, HttpStatus.OK);
    }

    // Endpoint para cancelar un pedido
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.ok("Pedido cancelado exitosamente.");
    }

    // Endpoint para listar todos los pedidos
    @GetMapping("/todos")
    public ResponseEntity<List<PedidoDTO>> listarTodosLosPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodosLosPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Endpoint para ver el detalle de un pedido
    @GetMapping("/detalle/{id}")
    public ResponseEntity<PedidoDTO> verDetallePedido(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.verDetallePedido(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    // Endpoint para obtener un pedido por su ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.obtenerPedido(id);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

}