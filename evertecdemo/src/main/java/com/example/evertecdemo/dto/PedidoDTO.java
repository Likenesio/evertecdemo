package com.example.evertecdemo.dto;

import java.time.LocalDate;
import java.util.List;

public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String estado;
    private LocalDate fecha;
    private List<ProductoPedidoDTO> productos;
    private double totalPedido;

    // DTO interno para los detalles del producto
    public static class ProductoPedidoDTO {
        private Long productoId;
        private String nombre;
        private double precio;
        private int cantidad;

        // Constructores, getters y setters
        public ProductoPedidoDTO() {
        }

        public ProductoPedidoDTO(Long productoId, String nombre, double precio, int cantidad) {
            this.productoId = productoId;
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
        }

        // Getters y setters
        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }

    // Constructores, getters y setters actualizados
    public PedidoDTO() {
    }

    // Constructor actualizado
    public PedidoDTO(Long id, Long clienteId, String clienteNombre, String estado,
            LocalDate fecha, List<ProductoPedidoDTO> productos, double totalPedido) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.estado = estado;
        this.fecha = fecha;
        this.productos = productos;
        this.totalPedido = totalPedido;
    }

    // Getters y setters actualizados
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<ProductoPedidoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoPedidoDTO> productos) {
        this.productos = productos;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", clienteNombre='" + clienteNombre + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha=" + fecha +
                ", productos=" + productos +
                ", totalPedido=" + totalPedido +
                '}';
    }

}
