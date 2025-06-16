package br.com.erudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory; // RabbitMQ client factory
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.amqp.RabbitMQSender;

@Configuration
public class TracingConfig {

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");   // configure seu host RabbitMQ
        factory.setPort(5672);          // porta padrão RabbitMQ
        factory.setUsername("guest");   // usuário padrão
        factory.setPassword("guest");   // senha padrão
        return factory;
    }

    @Bean
    public Sender rabbitSender(ConnectionFactory rabbitConnectionFactory) {
        return RabbitMQSender.newBuilder()
                .connectionFactory(rabbitConnectionFactory)
                .queue("zipkin") // fila padrão zipkin
                .build();
    }

    @Bean
    public Reporter<zipkin2.Span> spanReporter(Sender sender) {
        return AsyncReporter.create(sender);
    }
}
