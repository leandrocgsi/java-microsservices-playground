package br.com.erudio;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BookServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    @RestController
    class ApiController {
        @Autowired
        private RabbitTemplate rabbitTemplate;

        @GetMapping("/send")
        public String sendMessage() {
            rabbitTemplate.convertAndSend("exchange", "key", "Hello from book-service");
            return "Message sent";
        }
    }

    @Component
    class MessageListener {
        @RabbitListener(queues = "queue")
        public void receive(String message) {
            System.out.println("Received message: " + message);
        }
    }
}