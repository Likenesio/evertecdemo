package com.example.evertecdemo.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado; // ejemplo: "pendiente", "enviado", "entregado", "cancelado"
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "pedido_producto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;

    public Pedido() {
    }

    public Pedido(Long id, EstadoPedido estado, LocalDate fecha, Cliente cliente, List<Producto> productos) {
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.cliente = cliente;
        this.productos = productos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", estado=" + estado +
                ", fecha=" + fecha +
                ", cliente=" + cliente +
                ", productos=" + productos +
                '}';
    }
}
