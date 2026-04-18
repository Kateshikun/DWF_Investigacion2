package sv.edu.udb.sistemapedidos.publisher;

import sv.edu.udb.sistemapedidos.config.RabbitConfig;
import sv.edu.udb.sistemapedidos.dto.OrderCreatedEvent; // Import corregido
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarPedido(OrderCreatedEvent evento) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                evento
        );
        System.out.println(" [PRODUCTOR] Evento enviado a RabbitMQ: OrderCreatedEvent para " + evento.getCliente());
    }
}