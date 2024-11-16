package com.example.evertecdemo.dto;

import java.util.List;

public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private String estado;
    private List<Long> productosIds;


    public PedidoDTO() {
    }

    public PedidoDTO(Long id, Long clienteId, String estado, List<Long> productosIds) {
        this.id = id;
        this.clienteId = clienteId;
        this.estado = estado;
        this.productosIds = productosIds;
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Long> getProductosIds() {
        return productosIds;
    }

    public void setProductosIds(List<Long> productosIds) {
        this.productosIds = productosIds;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", estado='" + estado + '\'' +
                ", productosIds=" + productosIds +
                '}';
    }
}
