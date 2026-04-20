package sv.edu.udb.sistemapedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.sistemapedidos.dto.OrderCreatedEvent;
import sv.edu.udb.sistemapedidos.model.pedido;
import sv.edu.udb.sistemapedidos.publisher.PedidoPublisher;
import sv.edu.udb.sistemapedidos.repository.PedidoRepository;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoPublisher pedidoPublisher;

    @PostMapping
    public pedido crearPedido(@RequestBody pedido pedido) {
        // 1. Guardar en DB
        pedido guardado = pedidoRepository.save(pedido);

        // 2. Crear el evento con los campos EXACTOS de tu nueva entidad
        OrderCreatedEvent evento = new OrderCreatedEvent(
                guardado.getId(),
                guardado.getNombreCliente(), //
                "Pedido de " + guardado.getNombreCliente(), //
                guardado.getTotal()
        );

        // 3. Enviar a RabbitMQ
        pedidoPublisher.enviarPedido(evento);

        return guardado;
    }
}
