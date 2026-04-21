package sv.edu.udb.sistemapedidos.publisher;

import sv.edu.udb.sistemapedidos.config.RabbitConfig;
import sv.edu.udb.sistemapedidos.model.pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Componente que se encarga de enviar mensajes a RabbitMQ
// Es el productor en nuestra arquitectura de mensajería
@Component
public class PedidoPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Envía un objeto pedido a RabbitMQ
    // Este método es llamado por OrderEventListener cuando se dispara un evento
    public void enviarPedido(pedido pedido) {
        try {
            // rabbitTemplate.convertAndSend hace la magia:
            // 1. Convierte el objeto 'pedido' a JSON automáticamente
            // 2. Lo envía al exchange configurado en RabbitConfig
            // 3. El exchange lo distribuye a todas las colas vinculadas
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE_NAME,  // Nombre del exchange (fanout)
                    "",                          // Routing key vacío porque es FanoutExchange
                    pedido                       // El objeto que se convierte a JSON y se envía
            );
            
            // Log para saber que todo salió bien
            System.out.println(" [PRODUCTOR] Evento enviado a RabbitMQ: OrderCreatedEvent para " + pedido.getNombreCliente());
        } catch (Exception e) {
            // Si algo falla en la comunicación con RabbitMQ, lo logueamos y lanzamos la excepción
            System.err.println(" [PRODUCTOR] Error al enviar evento: " + e.getMessage());
            throw e;
        }
    }
}
