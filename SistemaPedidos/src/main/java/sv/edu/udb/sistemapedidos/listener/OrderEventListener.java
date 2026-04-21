package sv.edu.udb.sistemapedidos.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sv.edu.udb.sistemapedidos.model.pedido;
import sv.edu.udb.sistemapedidos.publisher.PedidoPublisher;

// ESTE ES EL PUENTE entre los eventos de Spring y RabbitMQ
// Escucha eventos de dominio y los convierte en mensajes de RabbitMQ
@Component
public class OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventListener.class); //Para meter mensajes en consola

    @Autowired
    private PedidoPublisher pedidoPublisher;

    // @EventListener hace que este método se ejecute cuando se publica un evento
    // El parámetro 'pedido' significa que escucha eventos de tipo 'pedido'
    // Cuando PedidoService llama a eventPublisher.publishEvent(guardado), este método se activa
    @EventListener
    @Async  // Esto hace que el procesamiento sea asíncrono (no bloquea el hilo principal)
    public void handleOrderCreatedEvent(pedido pedido) {
        try {
            logger.info("Evento de dominio recibido - Order ID: {}, Cliente: {}", 
                       pedido.getId(), pedido.getNombreCliente());
            
            // Una vez que recibimos el evento, lo enviamos a RabbitMQ
            // PedidoPublisher se encarga de la comunicación con el broker de mensajes
            pedidoPublisher.enviarPedido(pedido);
            
            logger.info("Evento publicado exitosamente en RabbitMQ");
        } catch (Exception e) {
            logger.error("Error al procesar evento de pedido: {}", e.getMessage(), e);
            // Si algo falla, lanzamos la excepción para que se maneje adecuadamente
            throw new RuntimeException("Error al procesar evento de pedido", e);
        }
    }
}
