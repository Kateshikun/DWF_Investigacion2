package sv.edu.udb.sistemapedidos.consumer;

import sv.edu.udb.sistemapedidos.config.RabbitConfig;
import sv.edu.udb.sistemapedidos.model.pedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// Este es el consumidor de RabbitMQ
// Escucha mensajes de la cola y los procesa
// Es el final del flujo de nuestra arquitectura de eventos
@Component
public class PedidoListener {

    // @RabbitListener hace que este método se ejecute cuando llega un mensaje a la cola
    // Spring se encarga de:
    // 1. Escuchar la cola especificada
    // 2. Deserializar el JSON a objeto 'pedido'
    // 3. Llamar a este método con el objeto ya convertido
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void recibirPedido(pedido pedido) {
        // Aquí iría la lógica de negocio para procesar el pedido
        // Por ahora solo lo mostramos en consola para demostrar que funciona
        
        System.out.println(" [CONSUMIDOR] ¡Evento OrderCreatedEvent Recibido!");
        System.out.println(" Pedido ID: " + pedido.getId() + " | Cliente: " + pedido.getNombreCliente());
        System.out.println(" Total: $" + pedido.getTotal() + " | Estado: " + pedido.getEstado());
        System.out.println(" Fecha: " + pedido.getFechaPedido());

    }
}
