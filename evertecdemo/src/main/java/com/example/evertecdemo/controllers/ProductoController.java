package com.example.evertecdemo.controllers;

import com.example.evertecdemo.dto.ProductoDTO;
import com.example.evertecdemo.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/nuevo")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProducto = productoService.crearProducto(productoDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public List<ProductoDTO> listarProductos() {
        return productoService.listarProductos();
    }

    @GetMapping("/listar/{id}")
    public ProductoDTO obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerProducto(id);
    }
}
