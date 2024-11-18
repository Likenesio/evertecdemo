package com.example.evertecdemo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.evertecdemo.dto.ClienteDTO;
import com.example.evertecdemo.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Endpoint para registrar un nuevo cliente
    @PostMapping("/registro")
    public ResponseEntity<ClienteDTO> registrarCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.registrarCliente(clienteDTO);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    // Endpoint para autenticar un cliente
    @PostMapping("/login")
    public ResponseEntity<String> autenticarCliente(@RequestBody ClienteDTO clienteDTO) {
        boolean autenticado = clienteService.autenticarCliente(clienteDTO.getEmail(), clienteDTO.getPassword());
        if (autenticado) {
            return new ResponseEntity<>("Autenticación exitosa", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }
    }
    //End Point para Listar todos los clientes
    @GetMapping("/listar")
    public List<ClienteDTO> obtenerCliente() {
    return clienteService.obtenerCliente();
}
}
