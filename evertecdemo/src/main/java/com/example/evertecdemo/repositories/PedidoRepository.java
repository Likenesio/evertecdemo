package com.example.evertecdemo.repositories;

import com.example.evertecdemo.models.EstadoPedido;
import com.example.evertecdemo.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByClienteIdAndEstado(Long clienteId, EstadoPedido estado);
}
