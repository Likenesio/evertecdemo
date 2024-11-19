package com.example.evertecdemo.services;

import com.example.evertecdemo.dto.ProductoDTO;
import com.example.evertecdemo.exceptions.RecursoNoEncontradoException;
import com.example.evertecdemo.models.Producto;
import com.example.evertecdemo.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Método para crear un nuevo producto
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        // Crear el objeto Producto a partir del DTO
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setCategoria(productoDTO.getCategoria());

        productoRepository.save(producto);

        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion(),
                producto.getStock(), producto.getCategoria());
    }

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll().stream()
                .map(producto -> new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(),
                        producto.getDescripcion(), producto.getStock(), producto.getCategoria()))
                .collect(Collectors.toList());
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
        return new ProductoDTO(producto.getId(), producto.getNombre(), producto.getPrecio(), producto.getDescripcion(),
                producto.getStock(), producto.getCategoria());
    }
}
