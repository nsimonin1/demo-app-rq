/**
 * 
 */
package org.simon.pascal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author simon.pascal.ngos
 *
 */
@Configuration
public class RabbitConfiguration {
	private Logger log=LoggerFactory.getLogger(getClass());
	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory=new CachingConnectionFactory();
		factory.setUri("amqp://swojgaxs:QSBTDwzjS1_o_drZQA0O1HQPDKybLVC6@bee.rmq.cloudamqp.com/swojgaxs");
		//factory.setHost(host);
		//factory.setUsername(username);
		//factory.setPassword(password);
		return factory;
	}
	
	@Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
	
	@Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }
	
	@Bean
    public Queue myQueue() {
       return new Queue("myqueue");
    }
	
	@Bean
	public SimpleMessageListenerContainer  factoryCreatedContainerSimpleListener(
			SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory) {
		SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
		endpoint.setQueueNames("myqueue");
	    endpoint.setMessageListener(message -> {
	       log.info(message.toString());
	    });
	    return rabbitListenerContainerFactory.createListenerContainer(endpoint);
	}
}
