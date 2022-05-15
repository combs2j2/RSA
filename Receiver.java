package com.jeremy.RSAProj;

import java.math.BigInteger;

import java.io.*;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	@Autowired
	private Cipher cipher;
	@Autowired
	private Parser parser;
	private BigInteger prime1;
	private BigInteger prime2;
	@Autowired
	private PrimeNumberGenerator png;
	
	public PrimeNumberGenerator getPng() {
		return png;
	}
	public void setPng(PrimeNumberGenerator png) {
		this.png = png;
	}
	public Cipher getCipher() {
		return cipher;
	}
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	public void setPrime1(BigInteger prime1) {
		this.prime1 = prime1;
	}
	public void setPrime2(BigInteger prime2) {
		this.prime2 = prime2;
	}
	public BigInteger calculatePhi() {
		BigInteger prime1Minus1 = prime1.subtract(BigInteger.ONE);
		BigInteger prime2Minus1 = prime2.subtract(BigInteger.ONE);
		return prime1Minus1.multiply(prime2Minus1);
	}
	public BigInteger generatePublicKey() {
		BigInteger publicKey = BigInteger.ZERO;
		
		Random rand = new Random();
		
		while(!(publicKey.gcd(calculatePhi()).equals(BigInteger.ONE))) {
			publicKey = BigInteger.valueOf(rand.nextInt(Integer.MAX_VALUE));
		}
		
		cipher.setExponent(publicKey.modInverse(calculatePhi()));
		cipher.setModulus(prime1.multiply(prime2));
		
		return publicKey.mod(calculatePhi());
	}
	
	public void setPrimes() {
		png.setLo(new BigInteger("2000"));
		png.setHi(new BigInteger("20000"));
		
		prime1 = png.getRandomPrime();
		prime2 = png.getRandomPrime();
	}
	
	public String receiveMessage(List<BigInteger> encryptedMessage) throws IOException {
		
		FileWriter ciphertextWriter = null;
		
		try {
			ciphertextWriter = new FileWriter("ciphertext.txt");
			
			String cipherText = parser.numericListToString(encryptedMessage);
			
			ciphertextWriter.write(cipherText);
			
			List<BigInteger> decryptedMessage = cipher.executeCipher(encryptedMessage);
			String originalMessage = parser.numericListToString(decryptedMessage);
			return originalMessage;
			
		} finally {
			ciphertextWriter.close();
		}
	}
	
}
