package com.example.evertecdemo.services;

import com.example.evertecdemo.dto.PedidoDTO;
import com.example.evertecdemo.dto.PedidoDTO.ProductoPedidoDTO;
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
import java.util.ArrayList;
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

    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        validarPedidoDTO(pedidoDTO);

        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cliente no encontrado con id: " + pedidoDTO.getClienteId()));

        List<ProductoPedidoDTO> productosConDetalles = new ArrayList<>();
        double totalPedido = 0.0;

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setFecha(LocalDate.now());

        // Procesar cada producto
        List<Producto> productos = new ArrayList<>();
        for (ProductoPedidoDTO productoDTO : pedidoDTO.getProductos()) {
            Producto producto = productoRepository.findById(productoDTO.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "Producto no encontrado con id: " + productoDTO.getProductoId()));

            productos.add(producto);

            double subtotal = producto.getPrecio() * productoDTO.getCantidad();
            totalPedido += subtotal;

            productosConDetalles.add(new ProductoPedidoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    productoDTO.getCantidad()));
        }

        pedido.setProductos(productos);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return new PedidoDTO(
                pedidoGuardado.getId(),
                cliente.getId(),
                cliente.getNombre(),
                pedidoGuardado.getEstado().toString(),
                pedidoGuardado.getFecha(),
                productosConDetalles,
                totalPedido);
    }

    public PedidoDTO obtenerPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Pedido no encontrado con id: " + id));
        return crearPedidoDTOCompleto(pedido);
    }

    public List<PedidoDTO> listarPedidosCliente(Long clienteId) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cliente no encontrado con id: " + clienteId));

        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::crearPedidoDTOCompleto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoDTO actualizarEstadoPedido(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Pedido no encontrado con id: " + id));

        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);

        return crearPedidoDTOCompleto(pedido);
    }

    @Transactional
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Pedido " + id + " no existe"));

        if (!pedido.getEstado().equals(EstadoPedido.PENDIENTE)) {
            throw new IllegalArgumentException("Solo se pueden cancelar pedidos en estado PENDIENTE.");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    public List<PedidoDTO> listarTodosLosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        if (pedidos.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay pedidos registrados.");
        }

        return pedidos.stream()
                .map(this::crearPedidoDTOCompleto)
                .collect(Collectors.toList());
    }

    private PedidoDTO crearPedidoDTOCompleto(Pedido pedido) {
        List<ProductoPedidoDTO> productosDTO = new ArrayList<>();
        double totalPedido = 0.0;

        for (Producto producto : pedido.getProductos()) {

            int cantidad = 1;
            double subtotal = producto.getPrecio() * cantidad;
            totalPedido += subtotal;

            productosDTO.add(new ProductoPedidoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    cantidad));
        }

        return new PedidoDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNombre(),
                pedido.getEstado().toString(),
                pedido.getFecha(),
                productosDTO,
                totalPedido);
    }

    // Método de validación para PedidoDTO
    private void validarPedidoDTO(PedidoDTO pedidoDTO) {
        if (pedidoDTO.getClienteId() == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo.");
        }
        if (pedidoDTO.getProductos() == null || pedidoDTO.getProductos().isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un producto en el pedido.");
        }
        for (ProductoPedidoDTO producto : pedidoDTO.getProductos()) {
            if (producto.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad del producto debe ser mayor a 0");
            }
        }
    }
}