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

import javax.validation.ValidationException;

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
        if (clienteDTO.getEmail() == null || clienteDTO.getEmail().isEmpty() || clienteDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new ValidationException("El correo electrónico ya existe");
        }
        if (clienteDTO.getPassword() == null || clienteDTO.getPassword().isEmpty()
                || clienteDTO.getPassword().isBlank()) {
            System.out.println("La contraseña es nula o vacía");
            throw new IllegalArgumentException("La contraseña no puede ser nula ni vacía");
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setPassword(passwordEncoder.encode(clienteDTO.getPassword()));

        clienteRepository.save(cliente);

        return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getEmail(), cliente.getPassword(),
                cliente.getTelefono(), cliente.getApellido());
    }

    public boolean autenticarCliente(String email, String password) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Los datos del usuario no coinciden"));
        return passwordEncoder.matches(password, cliente.getPassword());
    }

    public List<ClienteDTO> obtenerCliente() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::convertirAClienteDTO)
                .collect(Collectors.toList());
    }

    private ClienteDTO convertirAClienteDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getEmail(), cliente.getPassword(),
                cliente.getTelefono(), cliente.getApellido());
    }

    public void eliminarCliente(Long id) {
        List<Pedido> pedidosPendientes = pedidoRepository.findByClienteIdAndEstado(id, EstadoPedido.PENDIENTE);
        List<Pedido> pedidosEnviados = pedidoRepository.findByClienteIdAndEstado(id, EstadoPedido.ENVIADO);

        if (!pedidosPendientes.isEmpty() || !pedidosEnviados.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No se puede eliminar el cliente. Tiene pedidos pendientes o enviados.");
        }
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }
}
