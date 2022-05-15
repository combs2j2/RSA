package com.jeremy.RSAProj;

import java.io.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RsaProjApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(RsaProjApplication.class, args);
		
		FileReader plaintextReader = null;
		FileWriter plaintextWriter = null;
		
		try {
			plaintextReader = new FileReader("message.txt");
			plaintextWriter = new FileWriter("recoveredplaintext.txt");
			
			Sender alice = context.getBean(Sender.class);
			Receiver bob = context.getBean(Receiver.class);
			
			String message = "";
			
			int c;
			while ((c = plaintextReader.read()) != -1) {
	            message = message + (char) c;
	         }
			
			alice.setMessage(message);
			
			
			String recoveredPlaintext = alice.sendMessage(bob);
			
			plaintextWriter.write(recoveredPlaintext);
			
			System.out.println("Message recovered!!!");
			
		} finally {
			plaintextReader.close();
			plaintextWriter.close();
		}
	}

}
