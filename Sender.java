package com.jeremy.RSAProj;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	@Autowired
	private Cipher cipher;
	private String message;
	@Autowired
	private Parser parser;
	
	public Sender() {
		super();
	}
	public Cipher getCipher() {
		return cipher;
	}
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	
	public String sendMessage(Receiver bob) throws IOException {
		
		bob.setPrimes();
		cipher.setExponent(bob.generatePublicKey());
		cipher.setModulus(bob.getCipher().getModulus());
		parser.setModulus(bob.getCipher().getModulus());
		
		List<BigInteger> plainText = parser.stringToNumericList(message);
		
		List<BigInteger> cipherText = cipher.executeCipher(plainText);
		
		return bob.receiveMessage(cipherText);
	}
	
}
