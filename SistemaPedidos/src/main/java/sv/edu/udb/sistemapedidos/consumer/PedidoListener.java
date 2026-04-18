package sv.edu.udb.sistemapedidos.consumer;

import sv.edu.udb.sistemapedidos.config.RabbitConfig;
import sv.edu.udb.sistemapedidos.dto.OrderCreatedEvent; //
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void recibirPedido(OrderCreatedEvent evento) {
        System.out.println(" [CONSUMIDOR] ¡Evento OrderCreatedEvent Recibido!");
        System.out.println(" Pedido ID: " + evento.getId() + " | Cliente: " + evento.getCliente());
    }
}
