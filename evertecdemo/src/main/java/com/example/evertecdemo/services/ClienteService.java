package com.example.evertecdemo.services;

import com.example.evertecdemo.dto.ClienteDTO;
import com.example.evertecdemo.exceptions.RecursoNoEncontradoException;
import com.example.evertecdemo.models.Cliente;
import com.example.evertecdemo.models.EstadoPedido;
import com.example.evertecdemo.models.Pedido;
import com.example.evertecdemo.repositories.ClienteRepository;
import com.example.evertecdemo.repositories.PedidoRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final PedidoRepository pedidoRepository;

    public ClienteService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository,
            PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.pedidoRepository = pedidoRepository;
    }

    public ClienteDTO registrarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setComuna(clienteDTO.getComuna());
        cliente.setPassword(passwordEncoder.encode(clienteDTO.getPassword()));

        clienteRepository.save(cliente);
        return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getEmail(), cliente.getPassword(),
                cliente.getTelefono(), cliente.getDireccion(), cliente.getComuna(), cliente.getApellido());
    }

    public boolean autenticarCliente(String email, String password) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Los datos del usuario no coinciden"));
        return passwordEncoder.matches(password, cliente.getPassword());
    }

    public List<ClienteDTO> obtenerCliente() {
        List<Cliente> clientes = clienteRepository.findAll(); // Obtener la lista de clientes
        // Convertir cada Cliente a ClienteDTO usando un stream
        return clientes.stream()
                .map(this::convertirAClienteDTO) // Llama al método de conversión
                .collect(Collectors.toList());
    }

    private ClienteDTO convertirAClienteDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getEmail(), cliente.getPassword(),
                cliente.getTelefono(), cliente.getDireccion(), cliente.getComuna(), cliente.getApellido());
    }

    public void eliminarCliente(Long id) {
        List<Pedido> pedidosPendientes = pedidoRepository.findByClienteIdAndEstado(id, EstadoPedido.PENDIENTE);

        if (!pedidosPendientes.isEmpty()) {
            throw new RecursoNoEncontradoException("No se puede eliminar el cliente. Tiene pedidos pendientes.");
        }
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }
}
