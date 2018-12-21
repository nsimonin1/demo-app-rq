/**
 * 
 */
package org.simon.pascal.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author simon.pascal.ngos
 *
 */
@RestController
public class HelloController {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@GetMapping("/hello")
	public void hello() {
		rabbitTemplate.convertAndSend("myqueue", "foo");
		
	}
	
	@GetMapping("/ok")
	public ResponseEntity<String> receive(){
		return ResponseEntity.ok((String) rabbitTemplate.receiveAndConvert("myqueue"));
	}
}
