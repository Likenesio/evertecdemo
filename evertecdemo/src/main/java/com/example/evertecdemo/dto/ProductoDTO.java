package com.example.evertecdemo.dto;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private String descripcion;
    private Integer stock;
    private String categoria;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, String nombre, Double precio, String descripcion, Integer stock, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio + '\'' +
                ", stock=" + stock + '\'' +
                ", categoria=" + categoria + '\'' +
                ", descripcion='" + descripcion +
                '}';
    }
}
