package sv.edu.udb.sistemapedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.sistemapedidos.model.pedido;
import sv.edu.udb.sistemapedidos.repository.PedidoRepository;

import java.time.LocalDateTime;

// Servicio que contiene la lógica de negocio de pedidos
// Aquí es donde ocurre la magia de la arquitectura orientada a eventos
@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // ESTE ES EL COMPONENTE CLAVE para la arquitectura de eventos
    // ApplicationEventPublisher nos permite disparar eventos que otros componentes pueden escuchar
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // Crea un nuevo pedido y dispara un evento
    // Este método es el corazón del sistema de eventos
    public pedido crearPedido(pedido pedido) {
        // Seteamos valores por defecto si no vienen en el request
        if (pedido.getFechaPedido() == null || pedido.getFechaPedido().isEmpty()) {
            pedido.setFechaPedido(LocalDateTime.now().toString());
        }
        if (pedido.getEstado() == null || pedido.getEstado().isEmpty()) {
            pedido.setEstado("PENDIENTE");
        }

        // 1. Primero guardamos en la base de datos
        pedido guardado = pedidoRepository.save(pedido);

        // 2. ESTA ES LA PARTE IMPORTANTE: Disparamos un evento de dominio
        // El objeto 'guardado' (que es la entidad pedido) se convierte en un evento
        // Cualquier componente que esté escuchando eventos de tipo 'pedido' recibirá esto
        // Esto es lo que nos permite desacoplar la lógica de negocio de RabbitMQ
        eventPublisher.publishEvent(guardado);

        return guardado;
    }

    // Métodos CRUD simples que no disparan eventos
    // (solo crearPedido dispara eventos porque es la acción principal)

    public pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    public Iterable<pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll();
    }

    public pedido actualizarPedido(Long id, pedido pedidoActualizado) {
        pedido existente = obtenerPedidoPorId(id);
        
        existente.setNombreCliente(pedidoActualizado.getNombreCliente());
        existente.setTotal(pedidoActualizado.getTotal());
        existente.setFechaPedido(pedidoActualizado.getFechaPedido());
        existente.setEstado(pedidoActualizado.getEstado());

        return pedidoRepository.save(existente);
    }

    public void eliminarPedido(Long id) {
        pedido existente = obtenerPedidoPorId(id);
        pedidoRepository.delete(existente);
    }
}
