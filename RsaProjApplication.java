package com.jeremy.RSAProj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RsaProjApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RsaProjApplication.class, args);
		
		Sender alice = context.getBean(Sender.class);
		Receiver bob = context.getBean(Receiver.class);
		
		alice.setMessage("Hello, bob. My name is alice!");
		alice.sendMessage(bob);
	}

}
