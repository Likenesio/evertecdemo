package com.example.evertecdemo.services;

import com.example.evertecdemo.dto.PedidoDTO;
import com.example.evertecdemo.exceptions.RecursoNoEncontradoException;
import com.example.evertecdemo.models.Cliente;
import com.example.evertecdemo.models.EstadoPedido;
import com.example.evertecdemo.models.Pedido;
import com.example.evertecdemo.models.Producto;
import com.example.evertecdemo.repositories.ClienteRepository;
import com.example.evertecdemo.repositories.PedidoRepository;
import com.example.evertecdemo.repositories.ProductoRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

        private final PedidoRepository pedidoRepository;
        private final ClienteRepository clienteRepository;
        private final ProductoRepository productoRepository;

        public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                        ProductoRepository productoRepository) {
                this.pedidoRepository = pedidoRepository;
                this.clienteRepository = clienteRepository;
                this.productoRepository = productoRepository;
        }

        // Método para crear un nuevo pedido
        public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
                validarPedidoDTO(pedidoDTO);

                Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Cliente no encontrado con id: " + pedidoDTO.getClienteId()));

                // Mapeo de productos del pedido
                List<Producto> productos = pedidoDTO.getProductosIds().stream()
                                .map(id -> productoRepository.findById(id)
                                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                                "Producto no encontrado con id: " + id)))
                                .collect(Collectors.toList());

                Pedido pedido = new Pedido();
                pedido.setCliente(cliente);
                pedido.setProductos(productos);
                pedido.setEstado(EstadoPedido.PENDIENTE);
                pedido.setFecha(LocalDate.now());

                pedidoRepository.save(pedido);

                return mapearAPedidoDTO(pedido);
        }

        // Método para obtener un pedido por ID
        public PedidoDTO obtenerPedido(Long id) {
                Pedido pedido = pedidoRepository.findById(id)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Pedido no encontrado con id: " + id));
                return mapearAPedidoDTO(pedido);
        }

        // Método para listar todos los pedidos de un cliente
        public List<PedidoDTO> listarPedidosCliente(Long clienteId) {
                clienteRepository.findById(clienteId)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Cliente no encontrado con id: " + clienteId));

                return pedidoRepository.findByClienteId(clienteId).stream()
                                .map(this::mapearAPedidoDTO)
                                .collect(Collectors.toList());
        }

        // Método para actualizar el estado de un pedido
        @Transactional
        public PedidoDTO actualizarEstadoPedido(Long id, EstadoPedido nuevoEstado) {
                Pedido pedido = pedidoRepository.findById(id)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Pedido no encontrado con id: " + id));

                pedido.setEstado(nuevoEstado);
                pedidoRepository.save(pedido);

                return mapearAPedidoDTO(pedido);
        }

        // Método para cancelar un pedido
        @Transactional
        public void cancelarPedido(Long id) {
                Pedido pedido = pedidoRepository.findById(id)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Pedido no encontrado con id: " + id));

                if (!pedido.getEstado().equals(EstadoPedido.PENDIENTE)) {
                        throw new IllegalArgumentException("Solo se pueden cancelar pedidos en estado PENDIENTE.");
                }

                pedido.setEstado(EstadoPedido.CANCELADO);
                pedidoRepository.save(pedido);
        }

        // Método auxiliar para mapear Pedido a PedidoDTO
        private PedidoDTO mapearAPedidoDTO(Pedido pedido) {
                return new PedidoDTO(
                                pedido.getId(),
                                pedido.getCliente().getId(),
                                pedido.getEstado().toString(),
                                pedido.getProductos().stream().map(Producto::getId).collect(Collectors.toList()));
        }

        // Método de validación para PedidoDTO
        private void validarPedidoDTO(PedidoDTO pedidoDTO) {
                if (pedidoDTO.getClienteId() == null) {
                        throw new IllegalArgumentException("El ID del cliente no puede ser nulo.");
                }
                if (pedidoDTO.getProductosIds() == null || pedidoDTO.getProductosIds().isEmpty()) {
                        throw new IllegalArgumentException("Debe haber al menos un producto en el pedido.");
                }
        }

        // Método para listar todos los pedidos
        public List<PedidoDTO> listarTodosLosPedidos() {
                List<Pedido> pedidos = pedidoRepository.findAll();

                if (pedidos.isEmpty()) {
                        throw new RecursoNoEncontradoException("No hay pedidos registrados.");
                }

                return pedidos.stream()
                                .map(pedido -> new PedidoDTO(
                                                pedido.getId(),
                                                pedido.getCliente().getId(),
                                                pedido.getEstado().toString(),
                                                pedido.getProductos().stream().map(Producto::getId)
                                                                .collect(Collectors.toList())))
                                .collect(Collectors.toList());
        }

        // Método para ver el detalle de un pedido
        public PedidoDTO verDetallePedido(Long id) {
                Pedido pedido = pedidoRepository.findById(id)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "Pedido no encontrado con id: " + id));

                return new PedidoDTO(
                                pedido.getId(),
                                pedido.getCliente().getId(),
                                pedido.getEstado().toString(),
                                pedido.getProductos().stream().map(Producto::getId).collect(Collectors.toList()));
        }

}
