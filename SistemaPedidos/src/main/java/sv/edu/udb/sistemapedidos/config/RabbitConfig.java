package sv.edu.udb.sistemapedidos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuración de RabbitMQ para nuestro sistema de mensajería
// Aquí definimos las colas, exchanges y cómo se conectan entre sí
@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "pedidos_queue";
    public static final String EXCHANGE_NAME = "pedidos_exchange";

    // Define la cola donde se van a almacenar los mensajes
    // durable=true significa que la cola sobrevive a reinicios de RabbitMQ
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    // Define el exchange de tipo Fanout
    // FanoutExchange envía el mensaje a TODAS las colas vinculadas
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    // Conecta la cola con el exchange
    // Esto hace que los mensajes que lleguen al exchange se copien a nuestra cola
    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    // ESTE ES EL BEAN MÁS IMPORTANTE para la serialización
    // Jackson2JsonMessageConverter convierte automáticamente objetos Java a JSON y viceversa
    // Sin esto, RabbitMQ no sabría cómo manejar nuestros objetos 'pedido'
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(); //Da error porque esta desactualizado, pero funciona
        
        // DefaultClassMapper le dice a RabbitMQ qué paquetes son seguros para deserializar por seguridad
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("sv.edu.udb.sistemapedidos.model");
        converter.setClassMapper(classMapper);
        return converter;
    }
}
